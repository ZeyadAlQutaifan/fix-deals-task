package com.progressoft.common.service;


import com.progressoft.common.dto.FixDealDto;
import com.progressoft.common.interactor.FixDealsResponse;

import java.util.List;

public interface FixDealsService {
    FixDealsResponse saveDeals(List<FixDealDto> deals);
}
