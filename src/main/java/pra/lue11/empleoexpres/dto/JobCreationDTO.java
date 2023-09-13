package pra.lue11.empleoexpres.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import pra.lue11.empleoexpres.model.Job;
import pra.lue11.empleoexpres.model.enums.JobModality;
import pra.lue11.empleoexpres.model.enums.JobState;
import pra.lue11.empleoexpres.utils.validator.EnumVal;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author luE11 on 11/09/23
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobCreationDTO {
    @NotEmpty
    @Size(max = 100)
    protected String title;
    @EnumVal(enumClass = JobState.class, message = "Elija un estado")
    protected String state = JobState.ACTIVE.toString();
    @NotEmpty
    @Size(max = 300)
    protected String description;
    @CreationTimestamp
    protected LocalDateTime pubDate;
    @NotNull
    @Min(0)
    protected Double salary;
    @NotNull
    @Min(0)
    protected Double yearsOfExperience;
    @EnumVal(enumClass = JobModality.class, message = "Elija una modalidad")
    protected String jobMode;
    protected Integer placeId;
    @NotNull
    @Min(1)
    protected Integer professionId;

    public Job toJob(){
        return new Job(title, JobState.valueOf(state), description, pubDate, salary,
                yearsOfExperience, JobModality.valueOf(jobMode));
    }
}
