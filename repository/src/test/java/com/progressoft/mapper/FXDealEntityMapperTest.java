package com.progressoft.mapper;

import com.progressoft.common.dto.FXDealDto;
import com.progressoft.entity.FXDealEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class FXDealEntityMapperTest {


    @InjectMocks
    private FXDealEntityMapper FXDealEntityMapper;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testToEntity() {
        // Mock current LocalDateTime

        // Create a FXDealDto for testing
        FXDealDto FXDealDto = new FXDealDto("1", "USD", "EUR", BigDecimal.valueOf(100.00));

        // Map FXDealDto to FXDealEntity
        FXDealEntity FXDealEntity = FXDealEntityMapper.toEntity(FXDealDto);

        // Assertions
        assertEquals(FXDealDto.getDealId(), FXDealEntity.getDealId());
        assertEquals(FXDealDto.getDealAmount(), FXDealEntity.getDealAmount());
        assertEquals(FXDealDto.getFromCurrencyIsoCode(), FXDealEntity.getFromCurrencyIsoCode());
        assertEquals(FXDealDto.getToCurrencyIsoCode(), FXDealEntity.getToCurrencyIsoCode());
    }
}