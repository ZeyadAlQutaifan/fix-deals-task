package com.progressoft.common.interactor;

import com.progressoft.common.dto.FXDealDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class FxDealsResponse {
    private String message;
    private List<UnsavedDeal> unSavedDeals;
    private List<FXDealDto> savedDeals;
}
