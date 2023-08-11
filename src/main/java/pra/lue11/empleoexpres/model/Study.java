package pra.lue11.empleoexpres.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * @author luE11 on 9/08/23
 */
@Entity
@Table(name = "studies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Study {
    @Id
    @Column(name = "study_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected Integer id;
    @Column(name = "certificate_name", length = 100, unique = true, nullable = false)
    protected String certificateName;
    @JsonIgnore
    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<PersonHasStudy> persons;

}
