package br.com.akross.sdpcorepattern;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SdpCorePatternValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SdpCorePattern {
    String message() default "Message default";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
