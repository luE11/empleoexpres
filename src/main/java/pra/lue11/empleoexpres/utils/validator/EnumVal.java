package pra.lue11.empleoexpres.utils.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author luE11 on 4/07/23
 */
@Target( { ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EnumValidator.class)
public @interface EnumVal {

    //error message
    public String message() default "Value is invalid";
    //represents group of constraints
    public Class<?>[] groups() default {};
    //represents additional information about annotation
    public Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();
}
