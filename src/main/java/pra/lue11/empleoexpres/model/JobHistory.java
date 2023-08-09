package pra.lue11.empleoexpres.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author luE11 on 1/08/23
 */
@Entity
@Table(name = "job_histories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobHistory {
    @Id
    @Column(name = "jobhistory_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected Integer id;
    @NotEmpty
    @Column(length = 200)
    protected String description;
    @NotEmpty
    @Column(length = 50)
    protected String position;
    @Column(name = "init_date", nullable = false)
    protected LocalDate initDate;
    @Column(name = "end_date")
    protected LocalDate endDate;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id", nullable = false)
    protected Person candidate;

    public JobHistory(String description, String position, LocalDate initDate, LocalDate endDate) {
        this.description = description;
        this.position = position;
        this.initDate = initDate;
        this.endDate = endDate;
    }
}
