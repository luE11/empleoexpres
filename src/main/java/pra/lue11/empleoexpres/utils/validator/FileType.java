package pra.lue11.empleoexpres.utils.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target( { ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = FileTypeValidator.class)
public @interface FileType {

    //error message
    public String message() default "Uploaded file is invalid";
    //represents group of constraints
    public Class<?>[] groups() default {};
    //represents additional information about annotation
    public Class<? extends Payload>[] payload() default {};
    String[] typesAllowed() default {};

    boolean nullable() default true;
}
