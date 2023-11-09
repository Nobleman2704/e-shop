package com.example.eshop.annotation;

import com.example.eshop.enums.CardType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CardTypeValidator implements ConstraintValidator<ValidCardTypePattern, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        value = value.toUpperCase();
        return value.equals(CardType.UZ_CARD.name())
                || value.equals(CardType.MASTER_CARD.name()) ||
                value.equals(CardType.HUMO.name()) ||
                value.equals(CardType.VISA.name());
    }
}
