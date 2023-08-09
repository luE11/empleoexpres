package pra.lue11.empleoexpres.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author luE11 on 9/08/23
 */
@Entity
@NoArgsConstructor
@Table(name = "persons_has_studies")
@IdClass(PersonStudyId.class)
@Getter
@Setter
public class PersonHasStudy {
    @Id @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;
    @Id @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;
    @NotEmpty
    @Column(name = "entity_name", length = 100)
    protected String entityName;
    @Column(name = "start_date", nullable = false)
    protected LocalDate startDate;
    @Column(name = "end_date")
    protected LocalDate endDate;
    @Column(length = 200)
    protected String description;

    public PersonHasStudy(Person person, Study study) {
        this.person = person;
        this.study = study;
    }
}
