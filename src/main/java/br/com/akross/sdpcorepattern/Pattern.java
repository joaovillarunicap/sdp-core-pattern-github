package br.com.akross.sdpcorepattern;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Pattern {
    RegexValidator value() default RegexValidator.DEFAULT;

    RegexValidator secondValue() default RegexValidator.DEFAULT;
}
