package pra.lue11.empleoexpres.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import pra.lue11.empleoexpres.model.enums.JobModality;
import pra.lue11.empleoexpres.model.enums.JobState;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * @author luE11 on 16/08/23
 */
@Entity
@Table(name = "jobs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    @Id
    @Column(name = "job_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected Integer id;
    @Column(name = "title", nullable = false, length = 100)
    protected String title;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "state", nullable = false)
    protected JobState state;
    @Column(name = "description", nullable = false, length = 1000)
    protected String description;
    @Column(name = "pub_date", nullable = false)
    @CreationTimestamp
    protected LocalDateTime pubDate;
    @Column(name = "salary", nullable = false) // TODO: Componente selector rango de precios?
    @Min(0)
    protected Double salary;
    @Column(name = "years_of_experience", nullable = false)
    @Min(0)
    protected Double yearsOfExperience;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "job_mode", nullable = false)
    protected JobModality jobMode;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "place_id", referencedColumnName = "place_id")
    protected Place location;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "profession_id", referencedColumnName = "study_id", nullable = false)
    protected Study profession;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "publisher_id", referencedColumnName = "publisher_id", nullable = false)
    protected Publisher publisher;

    public Job(String title, JobState state, String description, Double salary, Double yearsOfExperience, JobModality jobMode) {
        this.title = title;
        this.state = state;
        this.description = description;
        this.salary = salary;
        this.yearsOfExperience = yearsOfExperience;
        this.jobMode = jobMode;
    }

    public boolean isAllJobMode(){
        return jobMode.compareTo(JobModality.ALL)==0;
    }

    public String getTruncateDescription(int length){
        return description.length()<=length ? description : description.substring(0, length)+"...";
    }

    public String getPubDateTime(){
        Period p = Period.between(pubDate.toLocalDate(), LocalDate.now());
        Duration d = Duration.between(pubDate, LocalDateTime.now());
        StringBuilder dateDiff = new StringBuilder();
        if(p.getYears()>0)
            dateDiff.append("Hace ").append(p.getYears()).append(" años");
        else if(p.getMonths()>0)
            dateDiff.append("Hace ").append(p.getMonths()).append(" meses");
        else if(p.getDays()>0)
            dateDiff.append("Hace ").append(p.getDays()).append(" días");
        else if(d.getSeconds()>=60)
            if(d.getSeconds()>=(60*60))
                dateDiff.append("Hace ").append(Math.round((float) d.getSeconds() / (60*60))).append(" horas");
            else
                dateDiff.append("Hace ").append(Math.round((float) d.getSeconds() / 60)).append(" minutos");
        else
            dateDiff.append("Hace ").append(d.getSeconds()).append(" segundos");
        return dateDiff.toString();
    }
}
