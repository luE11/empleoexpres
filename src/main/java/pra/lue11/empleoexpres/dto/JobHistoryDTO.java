package pra.lue11.empleoexpres.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pra.lue11.empleoexpres.model.JobHistory;

import java.time.LocalDate;

/**
 * @author luE11 on 1/08/23
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobHistoryDTO {
    @NotEmpty
    @Size(max = 200)
    protected String description;
    @NotEmpty
    protected String position;
    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    protected LocalDate initDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    protected LocalDate endDate;

    public JobHistory generateJobHistory(){
        return new JobHistory(description, position, initDate, endDate);
    }

}
