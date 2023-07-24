package pra.lue11.empleoexpres.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import pra.lue11.empleoexpres.model.Publisher;
import pra.lue11.empleoexpres.model.User;
import pra.lue11.empleoexpres.model.enums.UserRole;
import pra.lue11.empleoexpres.utils.validator.FileType;

/**
 * @author luE11 on 18/07/23
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublisherDTO {
    @Email
    @NotEmpty
    protected String email;
    @NotEmpty
    @Length(min = 8, max = 20)
    protected String password;
    @NotEmpty
    protected String name;
    @NotEmpty
    @Length(min = 20, max = 300)
    protected String description;
    @FileType(typesAllowed = { "jpg", "png" }, message = "El logo debe ser una imagen con formato .jpg o .png")
    protected MultipartFile logo = null;
    @NotNull
    protected boolean visible;

    public User generateUser(){
        return new User(email, password, UserRole.PUBLISHER);
    }

    public Publisher generateCompany(){
        return new Publisher(name, description, visible);
    }

}
