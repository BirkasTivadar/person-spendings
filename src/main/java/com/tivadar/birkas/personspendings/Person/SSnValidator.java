package com.tivadar.birkas.personspendings.Person;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SSnValidator implements ConstraintValidator<Ssn, String> {

    String regex = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";

    Pattern pattern = Pattern.compile(regex);

    @Override
    public boolean isValid(String ssn, ConstraintValidatorContext constraintValidatorContext) {
        Matcher matcher = createMatcher(ssn);
        return ssn != null &&
                !ssn.isBlank() &&
                ssn.length() == Person.SSN_LENGTH &&
                matcher.matches();
    }

    private Matcher createMatcher(String ssn) {
        String matcherSsn = ssn.substring(0, 3) + "-" + ssn.substring(3, 5) + "-" + ssn.substring(5, 9);
        return pattern.matcher(matcherSsn);
    }

    @Override
    public void initialize(Ssn constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
