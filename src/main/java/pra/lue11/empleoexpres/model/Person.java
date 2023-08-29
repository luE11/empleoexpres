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
import java.util.List;
import java.util.Set;

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
    @Column(length = 50)
    protected String position;
    @Column(name = "created_at")
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

    @JsonIgnore
    @OneToMany(mappedBy = "candidate")
    protected List<JobHistory> jobHistories;

    @JsonIgnore
    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<PersonHasStudy> studies;

    @JsonIgnore
    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<JobHasCandidate> jobs;

    public Person(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String description, JobModality preferredModality, String address, String position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.preferredModality = preferredModality;
        this.address = address;
        this.position = position;
    }

    public String getCreatedAtAsString() {
        return createdAt.format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, 'a las' hh:mm a"));
    }

    public String getFullName(){
        return firstName+" "+lastName;
    }

    public String getCityName() { return place.getName(); }

    public String getModality() { return preferredModality.getModality(); }

    public int getCvCount() {
        int num = 1;
        if(cv2Url!=null && !cv2Url.isEmpty()) num++;
        if(cv3Url!=null && !cv3Url.isEmpty()) num++;
        return num;
    };

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        Person person = (Person) object;
        return user.getEmail().equals(person.user.email);
    }
}
