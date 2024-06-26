package com.progressoft.app.config;

import com.progressoft.common.dao.FxDealDAO;
import com.progressoft.common.service.FxDealsService;
import com.progressoft.service.fixdeal.FxDealsServiceImpl;
import com.progressoft.validate.DealInputValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public FxDealsService fixDealsService(FxDealDAO fxDealDAO,
                                          DealInputValidator dealInputValidator) {
        return new FxDealsServiceImpl(fxDealDAO, dealInputValidator);
    }
}
