package pra.lue11.empleoexpres.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author luE11 on 21/07/23
 */
@Entity
@Table(name = "publishers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Publisher {
    @Id
    @Column(name = "publisher_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected Integer id;
    @Column(length = 45, nullable = false)
    protected String companyName;
    @Column(length = 300, nullable = false)
    protected String description;
    @Column(length = 200)
    protected String logoUrl;
    @Column(nullable = false)
    protected boolean visible;
    @Column(name = "created_at", updatable = false, insertable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    protected User user;

    public Publisher(String companyName, String description, boolean visible) {
        this.companyName = companyName;
        this.description = description;
        this.visible = visible;
    }

    public String getCreatedAtAsString() {
        return createdAt.format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, 'a las' hh:mm a"));
    }
}
