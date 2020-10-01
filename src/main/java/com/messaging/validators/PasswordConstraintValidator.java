package com.messaging.validators;

import com.google.common.base.Joiner;
import org.passay.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Arrays;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;


public class PasswordConstraintValidator implements ConstraintValidator<PasswordConstraintValidator.ValidPassword, String> {

    private static final PasswordValidator PASSWORD_VALIDATOR = new PasswordValidator(Arrays.asList(
            new LengthRule(8, 30),
            new UppercaseCharacterRule(1),
            new DigitCharacterRule(1),
            new SpecialCharacterRule(1),
//            new NumericalSequenceRule(3, false),
//            new AlphabeticalSequenceRule(3, false),
            new WhitespaceRule()));

    @Override
    public void initialize(ValidPassword arg0) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        RuleResult result = PASSWORD_VALIDATOR.validate(new PasswordData(password));
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                Joiner.on(", ").join(PASSWORD_VALIDATOR.getMessages(result))).addConstraintViolation();
        return result.isValid();
    }

    @Documented
    @Constraint(validatedBy = PasswordConstraintValidator.class)
    @Target({TYPE, FIELD, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    public @interface ValidPassword {

        String message() default "Invalid Password";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

    }

}