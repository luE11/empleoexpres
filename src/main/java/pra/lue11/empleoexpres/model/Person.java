package pra.lue11.empleoexpres.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import pra.lue11.empleoexpres.model.enums.JobModality;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author luE11 on 18/07/23
 */
@Entity
@Table(name = "persons")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected Integer id;
    @Column(name = "first_name", nullable = false, length = 45)
    protected String firstName;
    @Column(name = "last_name", nullable = false, length = 45)
    protected String lastName;
    @Column(name = "birth_date", nullable = false)
    protected LocalDate birthDate;
    @Column(name = "phone_number", length = 20)
    protected String phoneNumber;
    @Column(length = 500)
    protected String description;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "preferred_modality", nullable = false)
    protected JobModality preferredModality;
    @Column(name = "photo_url", length = 200)
    protected String photoUrl;
    @Column(length = 45)
    protected String address;
    @Column(length = 200, nullable = false)
    protected String cv1Url;
    @Column(length = 200)
    protected String cv2Url;
    @Column(length = 200)
    protected String cv3Url;
    @Column(name = "created_at", updatable = false, insertable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    protected User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "place_id", referencedColumnName = "place_id", nullable = false)
    protected Place place;

    public Person(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String description, JobModality preferredModality, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.preferredModality = preferredModality;
        this.address = address;
    }

    public String getCreatedAtAsString() {
        return createdAt.format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, 'a las' hh:mm a"));
    }
}
