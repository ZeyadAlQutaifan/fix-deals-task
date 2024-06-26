package com.progressoft.mapper;

import com.progressoft.common.dto.FXDealDto;
import com.progressoft.entity.FXDealEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FXDealEntityMapper {
    public FXDealEntity toEntity(FXDealDto FXDealDto) {
        FXDealEntity FXDealEntity = new FXDealEntity();
        FXDealEntity.setDealAmount(FXDealDto.getDealAmount());
        FXDealEntity.setDealId(FXDealDto.getDealId());
        FXDealEntity.setToCurrencyIsoCode(FXDealDto.getToCurrencyIsoCode());
        FXDealEntity.setFromCurrencyIsoCode(FXDealDto.getFromCurrencyIsoCode());
        FXDealEntity.setDealTimestamp(LocalDateTime.now());
        return FXDealEntity;
    }
}