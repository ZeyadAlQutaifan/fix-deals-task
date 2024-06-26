package com.progressoft.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Currency;
import java.util.Set;

public class CurrencyISOUtils {

    public static boolean isValidCurrencyCode(String code) {
        if (StringUtils.isBlank(code)) {
            return false;
        }
        Set<Currency> availableCurrencies = Currency.getAvailableCurrencies();
        for (Currency currency : availableCurrencies) {
            if (currency.getCurrencyCode().equals(code.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

}
