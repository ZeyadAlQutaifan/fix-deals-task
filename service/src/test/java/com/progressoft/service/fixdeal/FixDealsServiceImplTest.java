package com.progressoft.service.fixdeal;


import com.progressoft.common.dao.FixDealDAO;
import com.progressoft.common.dto.FixDealDto;
import com.progressoft.common.exception.DuplicateFixDealException;
import com.progressoft.common.exception.InvalidDealException;
import com.progressoft.common.interactor.FixDealsResponse;
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


public class FixDealsServiceImplTest {

    @Mock
    private FixDealDAO fixDealDAO;

    @Mock
    private DealInputValidator dealInputValidator;

    @InjectMocks
    private FixDealsServiceImpl fixDealsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveDeals_AllSuccess() {
        List<FixDealDto> inputFixDealDtoList = Arrays.asList(
                new FixDealDto("1", "USD", "EUR", new BigDecimal("100.00")),
                new FixDealDto("2", "EUR", "USD", new BigDecimal("200.00"))
        );

        doNothing().when(dealInputValidator).validateDeal(any());

        doNothing().when(fixDealDAO).persist(any());

        FixDealsResponse response = fixDealsService.saveDeals(inputFixDealDtoList);

        assertEquals("OK", response.getMessage());
        assertEquals(0, response.getUnSavedDeals().size());
        assertEquals(inputFixDealDtoList.size(), response.getSavedDeals().size());
    }

    @Test
    public void testSaveDeals_ContainDuplicate() {
        List<FixDealDto> inputFixDealDtoList = Arrays.asList(
                new FixDealDto("1", "USD", "EUR", new BigDecimal("100.00")),
                new FixDealDto("2", "EUR", "USD", new BigDecimal("200.00"))
        );

        doNothing().when(dealInputValidator).validateDeal(any());

        doThrow(new DuplicateFixDealException("Deal already exists")).when(fixDealDAO).persist(any());

        FixDealsResponse response = fixDealsService.saveDeals(inputFixDealDtoList);

        // Assertions
        assertEquals("OK", response.getMessage());
        assertEquals(inputFixDealDtoList.size(), response.getUnSavedDeals().size());
    }

    @Test
    public void testSaveDeals_InvalidInput() {
        List<FixDealDto> inputFixDealDtoList = List.of(
                new FixDealDto("1", null, "EUR", new BigDecimal("100.00")) // Invalid input: fromCurrencyIsoCode is null
        );

        doThrow(new InvalidDealException("Invalid input: fromCurrencyIsoCode cannot be null")).when(dealInputValidator).validateDeal(any());
        FixDealsResponse response = fixDealsService.saveDeals(inputFixDealDtoList);

        // Assertions
        assertEquals("OK", response.getMessage());
        assertEquals(1, response.getUnSavedDeals().size());
        assertEquals(0, response.getSavedDeals().size());
    }
}