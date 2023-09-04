package pra.lue11.empleoexpres.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * @author luE11 on 29/08/23
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JobCandidateId {

    private int person;
    private int job;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        JobCandidateId that = (JobCandidateId) o;
        return Objects.equals(person, that.person) && Objects.equals(job, that.job);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, job);
    }
}
