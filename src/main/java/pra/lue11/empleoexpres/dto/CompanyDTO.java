package pra.lue11.empleoexpres.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import pra.lue11.empleoexpres.model.Company;
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
public class CompanyDTO {
    @Email
    @NotNull
    protected String email;
    @NotNull
    @Length(min = 8)
    protected String password;
    @NotNull
    protected String name;
    @NotNull
    protected String description;
    @FileType(typesAllowed = { "jpg", "png" })
    protected MultipartFile logo;
    @NotNull
    protected boolean visible;

    public User generateUser(){
        return new User(email, password, UserRole.PUBLISHER);
    }

    public Company generateCompany(){
        return new Company(name, description, visible);
    }

}
