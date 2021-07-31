package com.tivadar.birkas.personspendings.Person;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = SSnValidator.class)
public @interface Ssn {

    String message() default "SSN must be a special nine-digit number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
