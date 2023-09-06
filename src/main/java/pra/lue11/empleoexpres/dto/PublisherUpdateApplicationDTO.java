package pra.lue11.empleoexpres.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pra.lue11.empleoexpres.model.enums.JobApplicationState;
import pra.lue11.empleoexpres.utils.validator.EnumVal;

/**
 * @author luE11 on 5/09/23
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublisherUpdateApplicationDTO {
    @EnumVal(enumClass = JobApplicationState.class, message = "Debe seleccionar una opción")
    protected String state;
    @Size(max = 100)
    protected String companyObservations;
}
