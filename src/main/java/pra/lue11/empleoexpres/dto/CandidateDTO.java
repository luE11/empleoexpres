package pra.lue11.empleoexpres.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import pra.lue11.empleoexpres.model.Person;
import pra.lue11.empleoexpres.model.User;
import pra.lue11.empleoexpres.model.enums.JobModality;
import pra.lue11.empleoexpres.model.enums.UserRole;
import pra.lue11.empleoexpres.utils.validator.EnumVal;
import pra.lue11.empleoexpres.utils.validator.FileType;
import java.time.LocalDate;

/**
 * @author luE11 on 18/07/23
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDTO {
    @Email
    @NotEmpty
    protected String email;
    @NotEmpty
    @Size(min = 8)
    protected String password;
    @NotEmpty
    protected String firstName;
    @NotEmpty
    protected String lastName;
    @NotNull
    @Past(message = "Birth date can't be future")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    protected LocalDate birthDate;
    @Size(min = 10, max = 20)
    protected String phoneNumber;
    @Size(min = 8, max = 500)
    protected String description;
    @EnumVal(enumClass = JobModality.class, message = "Debe seleccionar una opción")
    protected String preferredModality;
    @FileType(typesAllowed = { "jpg", "png" })
    protected MultipartFile photo;
    @Size(min = 8)
    protected String address;
    @FileType(typesAllowed = { "pdf" }, nullable = false, message = "Debe cargar por lo menos una hoja de vida en formato .pdf")
    protected MultipartFile cv1;
    @FileType(typesAllowed = { "pdf" }, message = "El archivo debe ser de tipo .pdf")
    protected MultipartFile cv2;
    @FileType(typesAllowed = { "pdf" }, message = "El archivo debe ser de tipo .pdf")
    protected MultipartFile cv3;
    @NotNull(message = "Debe elegir una ciudad")
    protected Integer placeId;

    public User generateUser(){
        return new User(email, password, UserRole.CANDIDATE);
    }

    public Person generatePerson(){
        return new Person(firstName, lastName, birthDate, phoneNumber,
                description, JobModality.valueOf(preferredModality), address);
    }
}
