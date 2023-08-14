package pra.lue11.empleoexpres.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pra.lue11.empleoexpres.model.PersonHasStudy;

import java.time.LocalDate;

/**
 * @author luE11 on 14/08/23
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateStudyDTO {
    @NotNull
    @Min(1)
    private Integer studyId;
    @NotEmpty
    @Size(max = 100)
    private String entityName;
    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    protected LocalDate initDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    protected LocalDate endDate;
    @NotEmpty
    @Size(max = 200)
    protected String description;

    public PersonHasStudy toPersonHasStudy(){
        return new PersonHasStudy(entityName, initDate, endDate, description);
    }

}
