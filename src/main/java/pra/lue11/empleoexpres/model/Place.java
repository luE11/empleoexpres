package pra.lue11.empleoexpres.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author luE11 on 21/07/23
 */
@Entity
@Table(name = "places")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Place {
    @Id
    @Column(name = "place_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected Integer id;
    @Column(length = 50, unique = true, nullable = false)
    protected String name;

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                '}';
    }
}
