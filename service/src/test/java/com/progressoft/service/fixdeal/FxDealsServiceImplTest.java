package com.progressoft.service.fixdeal;


import com.progressoft.common.dao.FxDealDAO;
import com.progressoft.common.dto.FXDealDto;
import com.progressoft.common.exception.DuplicateFxDealException;
import com.progressoft.common.exception.InvalidDealException;
import com.progressoft.common.interactor.FxDealsResponse;
import com.progressoft.validate.DealInputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class FxDealsServiceImplTest {

    @Mock
    private FxDealDAO fxDealDAO;

    @Mock
    private DealInputValidator dealInputValidator;

    @InjectMocks
    private FxDealsServiceImpl fixDealsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveDeals_AllSuccess() {
        List<FXDealDto> inputFXDealDtoList = Arrays.asList(
                new FXDealDto("1", "USD", "EUR", new BigDecimal("100.00")),
                new FXDealDto("2", "EUR", "USD", new BigDecimal("200.00"))
        );

        doNothing().when(dealInputValidator).validateDeal(any());

        doNothing().when(fxDealDAO).persist(any());

        FxDealsResponse response = fixDealsService.saveDeals(inputFXDealDtoList);

        assertEquals("OK", response.getMessage());
        assertEquals(0, response.getUnSavedDeals().size());
        assertEquals(inputFXDealDtoList.size(), response.getSavedDeals().size());
    }

    @Test
    public void testSaveDeals_ContainDuplicate() {
        List<FXDealDto> inputFXDealDtoList = Arrays.asList(
                new FXDealDto("1", "USD", "EUR", new BigDecimal("100.00")),
                new FXDealDto("2", "EUR", "USD", new BigDecimal("200.00"))
        );

        doNothing().when(dealInputValidator).validateDeal(any());

        doThrow(new DuplicateFxDealException("Deal already exists")).when(fxDealDAO).persist(any());

        FxDealsResponse response = fixDealsService.saveDeals(inputFXDealDtoList);

        // Assertions
        assertEquals("OK", response.getMessage());
        assertEquals(inputFXDealDtoList.size(), response.getUnSavedDeals().size());
    }

    @Test
    public void testSaveDeals_InvalidInput() {
        List<FXDealDto> inputFXDealDtoList = List.of(
                new FXDealDto("1", null, "EUR", new BigDecimal("100.00")) // Invalid input: fromCurrencyIsoCode is null
        );

        doThrow(new InvalidDealException("Invalid input: fromCurrencyIsoCode cannot be null")).when(dealInputValidator).validateDeal(any());
        FxDealsResponse response = fixDealsService.saveDeals(inputFXDealDtoList);

        // Assertions
        assertEquals("OK", response.getMessage());
        assertEquals(1, response.getUnSavedDeals().size());
        assertEquals(0, response.getSavedDeals().size());
    }
}