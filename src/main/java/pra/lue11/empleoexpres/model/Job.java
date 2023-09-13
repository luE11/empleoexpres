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
import pra.lue11.empleoexpres.utils.LocalDateUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Set;

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
    @Column(name = "salary", nullable = false)
    @Min(0)
    protected Double salary;
    @Column(name = "years_of_experience", nullable = false)
    @Min(0)
    protected Double yearsOfExperience;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "job_mode", nullable = false)
    protected JobModality jobMode;
    @Column(name = "soft_deleted", nullable = false, columnDefinition = "boolean default false")
    protected boolean softDeleted = Boolean.FALSE;
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
    @JsonIgnore
    @OneToMany(mappedBy = "job", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<JobHasCandidate> candidates;

    public Job(String title, JobState state, String description, Double salary, Double yearsOfExperience, JobModality jobMode) {
        this.title = title;
        this.state = state;
        this.description = description;
        this.salary = salary;
        this.yearsOfExperience = yearsOfExperience;
        this.jobMode = jobMode;
    }

    public Job(String title, JobState state, String description, LocalDateTime pubDate, Double salary, Double yearsOfExperience, JobModality jobMode) {
        this.title = title;
        this.state = state;
        this.description = description;
        this.pubDate = pubDate;
        this.salary = salary;
        this.yearsOfExperience = yearsOfExperience;
        this.jobMode = jobMode;
    }

    public boolean isActive(){
        return this.state.compareTo(JobState.ACTIVE)==0;
    }

    public boolean isAllJobMode(){
        return jobMode.compareTo(JobModality.ALL)==0;
    }

    public String getTruncateDescription(int length){
        return description.length()<=length ? description : description.substring(0, length)+"...";
    }

    public String getPubDateAsString() {
        return pubDate.format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, 'a las' hh:mm a"));
    }

    public String getPubDateTime(){
        return LocalDateUtils.localDateTimeAsRecentTime(pubDate);
    }

    public int getApplicationCount(){
        return this.candidates.size();
    }
}
