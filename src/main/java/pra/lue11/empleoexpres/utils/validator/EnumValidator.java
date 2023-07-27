package pra.lue11.empleoexpres.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;


/**
 * @author luE11 on 4/07/23
 */
public class EnumValidator implements ConstraintValidator<EnumVal, String> {

    private List<String> valueList;

    @Override
    public void initialize(EnumVal constraintAnnotation) {
        valueList = new ArrayList<>();
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();

        @SuppressWarnings("rawtypes")
        Enum[] enumValArr = enumClass.getEnumConstants();

        for (@SuppressWarnings("rawtypes") Enum enumVal : enumValArr) {
            valueList.add(enumVal.toString().toUpperCase());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value!=null && valueList.contains(value.toUpperCase());
    }

}
