package pra.lue11.empleoexpres.model.inmutable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import java.time.LocalDate;

/**
 * View creation in data.sql file
 * @author luE11 on 11/08/23
 */
@Entity
@Table(name = "candidate_related_studies")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Immutable
@Subselect("select * from candidate_related_studies")
public class CandidateRelatedStudyView {
    @Id
    @Column(name = "id")
    @JsonIgnore
    private Integer id;
    @Column(name = "certificate_name")
    private String certificateName;
    @Column(name = "entity_name")
    private String entityName;
    @Column(name = "start_date")
    private LocalDate start_date;
    @Column(name = "end_date")
    private LocalDate end_date;
    @Column(name = "description")
    private String description;
    @JsonIgnore
    @Column(name = "person_id")
    private int personId;
}
