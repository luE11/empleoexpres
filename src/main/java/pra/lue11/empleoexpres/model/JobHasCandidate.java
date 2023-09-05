package pra.lue11.empleoexpres.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import pra.lue11.empleoexpres.model.enums.JobApplicationState;

import java.time.LocalDateTime;

/**
 * @author luE11 on 29/08/23
 */
@Entity
@NoArgsConstructor
@Table(name = "jobs_has_candidates")
@IdClass(JobCandidateId.class)
@Getter
@Setter
public class JobHasCandidate {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;
    @Id @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "state", nullable = false)
    private JobApplicationState state;
    @Column(name = "company_observations", length = 100)
    private String companyObservations;
    @Column(name = "candidate_comment", length = 200)
    private String candidateComment;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public JobHasCandidate(JobApplicationState state, String companyObservations, Person candidate, Job job) {
        this.state = state;
        this.companyObservations = companyObservations;
        this.job = job;
        this.person = candidate;
    }

    public JobHasCandidate(String candidateComment, Person candidate, Job job) {
        this.state = JobApplicationState.APPLIED;
        this.candidateComment = candidateComment;
        this.job = job;
        this.person = candidate;
    }


}
