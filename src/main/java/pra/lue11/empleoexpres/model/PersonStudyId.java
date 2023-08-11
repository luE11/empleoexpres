package pra.lue11.empleoexpres.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * @author luE11 on 9/08/23
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonStudyId {
    private int person;
    private int study;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PersonStudyId that = (PersonStudyId) o;
        return Objects.equals(person, that.person) && Objects.equals(study, that.study);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, study);
    }

}
