package com.progressoft.validate;

import com.progressoft.common.dto.FixDealDto;
import com.progressoft.common.exception.InvalidDealException;

import java.math.BigDecimal;

import static com.progressoft.common.utils.CurrencyISOUtils.isValidCurrencyCode;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class DealInputValidator {

    public void validateDeal(FixDealDto deal) throws InvalidDealException {
        validateDealId(deal.getDealId());
        validateCurrencyCode(deal.getFromCurrencyIsoCode(), "From Currency ISO Code");
        validateCurrencyCode(deal.getToCurrencyIsoCode(), "To Currency ISO Code");
        validateDealAmount(deal.getDealAmount());
    }

    private void validateDealId(String dealId) throws InvalidDealException {
        if (isBlank(dealId)) {
            throw new InvalidDealException("Deal Unique Id is missing.");
        }
    }

    private void validateCurrencyCode(String currencyCode, String fieldName) throws InvalidDealException {
        if (isBlank(currencyCode)) {
            throw new InvalidDealException(fieldName + " is missing.");
        }
        if (!isValidCurrencyCode(currencyCode)) {
            throw new InvalidDealException(fieldName + " is invalid.");
        }
    }

    private void validateDealAmount(BigDecimal dealAmount) throws InvalidDealException {
        if (isNull(dealAmount)) {
            throw new InvalidDealException("Deal Amount is invalid.");
        }
        if (dealAmount.compareTo(new BigDecimal("0.1")) <= 0) {
            throw new InvalidDealException("Deal Amount must be greater than or equal to 0.1");
        }
    }


}
