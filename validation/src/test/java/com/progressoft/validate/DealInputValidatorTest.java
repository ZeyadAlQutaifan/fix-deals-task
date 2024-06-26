package com.progressoft.validate;

import com.progressoft.common.dto.FixDealDto;
import com.progressoft.common.exception.InvalidDealException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DealInputValidatorTest {
    DealInputValidator dealInputValidator = new DealInputValidator();


    @Test
    void validateDeal_EmptyId() {
        assertThrows(InvalidDealException.class, () ->
                dealInputValidator.validateDeal(new FixDealDto(null, "USD", "JOD", new BigDecimal("23.3")))
        );
        assertThrows(InvalidDealException.class, () ->
                dealInputValidator.validateDeal(new FixDealDto("", "USD", "JOD", new BigDecimal("23.3")))
        );
    }

    @Test
    void validateDeal_EmptyFromCurrency() {
        assertThrows(InvalidDealException.class, () ->
                dealInputValidator.validateDeal(new FixDealDto("12", "", "JOD", new BigDecimal("23.3")))
        );
        assertThrows(InvalidDealException.class, () ->
                dealInputValidator.validateDeal(new FixDealDto("12", null, "JOD", new BigDecimal("23.3")))
        );
    }

    @Test
    void validateDeal_EmptyToCurrency() {
        assertThrows(InvalidDealException.class, () ->
                dealInputValidator.validateDeal(new FixDealDto("12", "USD", "", new BigDecimal("23.3")))
        );
        assertThrows(InvalidDealException.class, () ->
                dealInputValidator.validateDeal(new FixDealDto("12", "USD", null, new BigDecimal("23.3")))
        );
    }

    @Test
    void validateDeal_NullDealAmount() {
        assertThrows(InvalidDealException.class, () ->
                dealInputValidator.validateDeal(new FixDealDto("12", "USD", "JOD", null))
        );
        assertThrows(InvalidDealException.class, () ->
                dealInputValidator.validateDeal(new FixDealDto("12", "USD", "JOD", new BigDecimal("0.0")))
        );
    }

    @Test
    void validateDeal_LessThanMinimumAmount() {
        assertThrows(InvalidDealException.class, () ->
                dealInputValidator.validateDeal(new FixDealDto("12", "USD", "JOD", new BigDecimal("0.05")))
        );
    }

    @Test
    void validateDeal_ValidInput() throws InvalidDealException {
        // Valid input
        dealInputValidator.validateDeal(new FixDealDto("12", "USD", "JOD", new BigDecimal("23.3")));
        // No exception thrown means success
    }

    @Test
    void validateDeal_InvalidFromCurrency() {
        assertThrows(InvalidDealException.class, () ->
                dealInputValidator.validateDeal(new FixDealDto("12", "INVALID", "JOD", new BigDecimal("23.3")))
        );
    }

    @Test
    void validateDeal_InvalidToCurrency() {
        assertThrows(InvalidDealException.class, () ->
                dealInputValidator.validateDeal(new FixDealDto("12", "USD", "INVALID", new BigDecimal("23.3")))
        );
    }

}