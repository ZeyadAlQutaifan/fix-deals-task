package com.progressoft.common.dto;

import lombok.*;
import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FixDealDto {
    private String dealId ;
    private String fromCurrencyIsoCode;
    private String toCurrencyIsoCode;
    private BigDecimal dealAmount;
}
