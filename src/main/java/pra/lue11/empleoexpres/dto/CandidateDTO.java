package pra.lue11.empleoexpres.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import pra.lue11.empleoexpres.model.Person;
import pra.lue11.empleoexpres.model.User;
import pra.lue11.empleoexpres.model.enums.JobModality;
import pra.lue11.empleoexpres.model.enums.UserRole;
import pra.lue11.empleoexpres.utils.validator.EnumVal;
import pra.lue11.empleoexpres.utils.validator.FileType;

import java.util.Date;

/**
 * @author luE11 on 18/07/23
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDTO {
    @Email
    @NotNull
    protected String email;
    @NotNull
    @Length(min = 8)
    protected String password;
    @NotNull
    protected String firstName;
    @NotNull
    protected String lastName;
    @NotNull
    @Past(message = "Birth date can't be future")
    protected Date birthDate;
    @Length(min = 8)
    protected String phoneNumber;
    @Length(min = 8)
    protected String description;
    @NotNull
    @EnumVal(enumClass = JobModality.class)
    protected String preferredModality;
    @FileType(typesAllowed = { "jpg", "png" })
    protected MultipartFile photo;
    @Length(min = 8)
    protected String address;
    @NotNull
    @FileType(typesAllowed = { "pdf" })
    protected MultipartFile cv1;
    @FileType(typesAllowed = { "pdf" })
    protected MultipartFile cv2;
    @FileType(typesAllowed = { "pdf" })
    protected MultipartFile cv3;
    @NotNull
    protected int placeId;

    public User generateUser(){
        return new User(email, password, UserRole.CANDIDATE);
    }

    public Person generatePerson(){
        return new Person(firstName, lastName, birthDate, phoneNumber,
                description, JobModality.valueOf(preferredModality), address);
    }

}
