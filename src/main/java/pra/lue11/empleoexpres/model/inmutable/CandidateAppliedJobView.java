package pra.lue11.empleoexpres.model.inmutable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import pra.lue11.empleoexpres.model.enums.JobApplicationState;
import pra.lue11.empleoexpres.model.enums.JobModality;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author luE11 on 29/08/23
 */
@Entity
@Table(name = "candidate_applied_jobs")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Immutable
@Subselect("select * from candidate_applied_jobs")
public class CandidateAppliedJobView {
    @Id
    @Column(name = "id")
    @JsonIgnore
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "company_name")
    private String companyName;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "job_mode")
    private JobModality jobMode;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "state")
    private JobApplicationState state;
    @Column(name = "cv_url")
    private String cvUrl;
    @Column(name = "company_observations")
    private String companyObservations;
    @Column(name = "candidate_comment")
    private String candidateComment;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @JsonIgnore
    @Column(name = "person_id")
    private int personId;
    @JsonIgnore
    @Column(name = "job_id")
    private int jobId;

    public String getUpdatedAtAsString() {
        return updatedAt.format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, 'a las' hh:mm a"));
    }

}
