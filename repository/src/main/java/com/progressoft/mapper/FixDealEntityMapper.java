package com.progressoft.mapper;

import com.progressoft.common.dto.FixDealDto;
import com.progressoft.entity.FixDealEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FixDealEntityMapper {
    public FixDealEntity toEntity(FixDealDto fixDealDto) {
        FixDealEntity fixDealEntity = new FixDealEntity();
        fixDealEntity.setDealAmount(fixDealDto.getDealAmount());
        fixDealEntity.setDealId(fixDealDto.getDealId());
        fixDealEntity.setToCurrencyIsoCode(fixDealDto.getToCurrencyIsoCode());
        fixDealEntity.setFromCurrencyIsoCode(fixDealDto.getFromCurrencyIsoCode());
        fixDealEntity.setDealTimestamp(LocalDateTime.now());
        return fixDealEntity;
    }
}