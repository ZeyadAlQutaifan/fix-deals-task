package com.progressoft.mapper;

import com.progressoft.common.dto.FixDealDto;
import com.progressoft.entity.FixDealEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class FixDealEntityMapperTest {


    @InjectMocks
    private FixDealEntityMapper fixDealEntityMapper;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testToEntity() {
        // Mock current LocalDateTime

        // Create a FixDealDto for testing
        FixDealDto fixDealDto = new FixDealDto("1", "USD", "EUR", BigDecimal.valueOf(100.00));

        // Map FixDealDto to FixDealEntity
        FixDealEntity fixDealEntity = fixDealEntityMapper.toEntity(fixDealDto);

        // Assertions
        assertEquals(fixDealDto.getDealId(), fixDealEntity.getDealId());
        assertEquals(fixDealDto.getDealAmount(), fixDealEntity.getDealAmount());
        assertEquals(fixDealDto.getFromCurrencyIsoCode(), fixDealEntity.getFromCurrencyIsoCode());
        assertEquals(fixDealDto.getToCurrencyIsoCode(), fixDealEntity.getToCurrencyIsoCode());
    }
}