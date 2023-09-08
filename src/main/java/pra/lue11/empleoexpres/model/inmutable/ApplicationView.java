package pra.lue11.empleoexpres.model.inmutable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import pra.lue11.empleoexpres.model.enums.JobApplicationState;
import pra.lue11.empleoexpres.utils.LocalDateUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author luE11 on 29/08/23
 */
@Entity
@Table(name = "applications")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Immutable
@Subselect("select * from applications")
public class ApplicationView {
    @Id
    @Column(name = "id")
    @JsonIgnore
    private Integer id;
    @Column(name = "name")
    private String name;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "state")
    private JobApplicationState state;
    @Column(name = "cv_url")
    private String cvUrl;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @JsonIgnore
    @Column(name = "job_id")
    private int jobId;
    @JsonIgnore
    @Column(name = "person_id")
    private int personId;

    public String getLastUpdateTime(){
        return LocalDateUtils.localDateTimeAsRecentTime(updatedAt);
    }

}
