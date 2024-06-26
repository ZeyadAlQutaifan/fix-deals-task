package com.progressoft.common.interactor;

import com.progressoft.common.dto.FixDealDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class FixDealsResponse {
    private String message;
    private List<UnsavedDeal> unSavedDeals;
    private List<FixDealDto> savedDeals;
}
