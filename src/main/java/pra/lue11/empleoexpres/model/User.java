package pra.lue11.empleoexpres.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pra.lue11.empleoexpres.model.enums.UserRole;

/**
 * @author luE11 on 17/07/23
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected Integer id;
    @Column(length = 50, unique = true, nullable = false)
    protected String email;
    @Column(length = 60, nullable = false)
    protected String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected UserRole role;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    protected Person person;
    @OneToOne(mappedBy = "user")
    @JsonIgnore
    protected Company company;

    public User(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", role=" + role.getRoleName() +
                '}';
    }
}
