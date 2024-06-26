package com.progressoft.app.config;

import com.progressoft.common.dao.FixDealDAO;
import com.progressoft.common.service.FixDealsService;
import com.progressoft.service.fixdeal.FixDealsServiceImpl;
import com.progressoft.validate.DealInputValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public FixDealsService fixDealsService(FixDealDAO fixDealDAO,
                                           DealInputValidator dealInputValidator) {
        return new FixDealsServiceImpl(fixDealDAO, dealInputValidator);
    }
}
