package ir.parsdeveloper.model.validator.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.context.MessageSource;

import java.io.Serializable;


public abstract class BaseValidator<T> implements Serializable {

    private static MessageSource messageSource;

    public static MessageSource getMessageSource() {
        return messageSource;
    }

    @Autowired
    public static void setMessageSource(MessageSource messageSource) {
        BaseValidator.messageSource = messageSource;
    }

    public void validateEntitySingleView(T formObject, ValidationContext context) {

    }

    public void validateEntityListView(T formObject, ValidationContext context) {

    }
}
