package com.mehmetsolak.mini_chat_app.user.api.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxFileSizeValidator.class)
@Documented
public @interface MaxFileSize {
    String message() default "";
    long value();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
