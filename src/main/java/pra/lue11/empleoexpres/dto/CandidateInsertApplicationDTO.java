package pra.lue11.empleoexpres.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pra.lue11.empleoexpres.model.Job;
import pra.lue11.empleoexpres.model.JobHasCandidate;
import pra.lue11.empleoexpres.model.Person;

/**
 * @author luE11 on 5/09/23
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateInsertApplicationDTO {
    @Size(max = 200)
    protected String candidateComment;

    public JobHasCandidate toApplication(Job job, Person candidate){
        return new JobHasCandidate(candidateComment, candidate, job);
    }
}
