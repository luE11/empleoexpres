package pra.lue11.empleoexpres.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pra.lue11.empleoexpres.model.enums.JobModality;
import pra.lue11.empleoexpres.model.enums.JobState;

import java.time.LocalDate;

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
    @Column(name = "due_date", nullable = false)
    protected LocalDate dueDate;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "state", nullable = false)
    protected JobState state;
    @Column(name = "description", nullable = false, length = 500)
    protected String description;
    @Column(name = "pub_date", nullable = false)
    protected LocalDate pubDate;
    @Column(name = "salary_min", nullable = false) // TODO: Componente selector rango de precios?
    @Min(0)
    protected Double salaryMin;
    @Column(name = "salary_max", nullable = false)
    @Min(0)
    protected Double salaryMax;
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

    public Job(String title, LocalDate dueDate, JobState state, String description, LocalDate pubDate, Double salaryMin, Double salaryMax, Double yearsOfExperience, JobModality jobMode) {
        this.title = title;
        this.dueDate = dueDate;
        this.state = state;
        this.description = description;
        this.pubDate = pubDate;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.yearsOfExperience = yearsOfExperience;
        this.jobMode = jobMode;
    }
}
