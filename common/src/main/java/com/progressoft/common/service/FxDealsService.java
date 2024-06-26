package com.progressoft.common.service;


import com.progressoft.common.dto.FXDealDto;
import com.progressoft.common.interactor.FxDealsResponse;

import java.util.List;

public interface FxDealsService {
    FxDealsResponse saveDeals(List<FXDealDto> deals);
}
