package br.com.akross.sdpcorepattern;

import org.apache.commons.lang3.reflect.FieldUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.List;

import static java.util.Objects.nonNull;

public class SdpCorePatternValidator implements ConstraintValidator<SdpCorePattern, Object> {

    @Override
    public void initialize(SdpCorePattern constraintAnnotation) {
//        Nothing to initialize
    }

    @Override
    public boolean isValid(Object entity, ConstraintValidatorContext context) {
        var result = true;
        Class<?> clazz = entity.getClass();
        List<Field> allFields = FieldUtils.getAllFieldsList(clazz);
        for (var field : allFields) {
            Class<?> type = field.getType();
            Pattern annotation = field.getDeclaredAnnotation(Pattern.class);
            try {
                var strategy = SdpCorePatternTypeStrategy.getFromTypeName(type.getName());
                Object value = FieldUtils.readField(field, entity, true);
                if (nonNull(value) && nonNull(strategy)) {
                    List<RegexValidator> validatorList = strategy.getRegexValidatorList(annotation);
                    strategy.execute(value, validatorList);
                }
            } catch (SdpCorePatternException e) {
                result = false;
                buildCustomFiedError(context, e.getValidator().getErrorMessage(), field.getName());
                break;
            } catch (IllegalAccessException e) {
                throw new SdpCorePatternException(e);
            }
        }

        return result;
    }

    private void buildCustomFiedError(ConstraintValidatorContext context, String errorMessage, String fieldName) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorMessage)
                .addPropertyNode(fieldName)
                .addConstraintViolation();
    }
}
