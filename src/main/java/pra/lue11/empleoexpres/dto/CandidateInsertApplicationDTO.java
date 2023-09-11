package pra.lue11.empleoexpres.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pra.lue11.empleoexpres.model.Job;
import pra.lue11.empleoexpres.model.JobHasCandidate;
import pra.lue11.empleoexpres.model.Person;
import pra.lue11.empleoexpres.model.enums.JobApplicationState;

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
    @NotNull(message = "Debe elegir una hoja de vida registrada previamente")
    protected String cvUrl;

    public JobHasCandidate toApplication(Job job, Person candidate){
        return new JobHasCandidate(candidateComment, cvUrl, candidate, job);
    }
}
