package com.progressoft.controller;

import com.progressoft.common.dto.FixDealDto;
import com.progressoft.common.interactor.FixDealsResponse;
import com.progressoft.common.interactor.UnSaveReason;
import com.progressoft.common.interactor.UnsavedDeal;
import com.progressoft.common.service.FixDealsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class FixDealsControllerTest {
    @Mock
    private FixDealsService fixDealsService;

    @InjectMocks
    private FixDealsController fixDealsController;

    List<FixDealDto> inputFixDealDtoList;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        inputFixDealDtoList = new ArrayList<>(Arrays.asList(
                new FixDealDto("1", "USD", "EUR", new BigDecimal("100.00")),
                new FixDealDto("2", "EUR", "USD", new BigDecimal("200.00")),
                new FixDealDto("3", "EUR", "AED", new BigDecimal("30.00")),
                new FixDealDto("4", "JOD", "AED", new BigDecimal("30.00"))
        ));
    }

    @Test
    public void testPatchFixDeals_ContainDuplicate() {

        List<UnsavedDeal> unsavedDeals = List.of(
                new UnsavedDeal(inputFixDealDtoList.get(0).getDealId(), "Deal is duplicated", UnSaveReason.DUPLICATE)
        );
        inputFixDealDtoList.remove(0);
        FixDealsResponse expectedResponse = new FixDealsResponse("OK",
                unsavedDeals, inputFixDealDtoList);
        when(fixDealsService.saveDeals(inputFixDealDtoList)).thenReturn(expectedResponse);

        ResponseEntity<FixDealsResponse> responseEntity =
                fixDealsController.patchFixDeals(inputFixDealDtoList);
        List<UnsavedDeal> unSavedDeals = Objects.requireNonNull(responseEntity.getBody()).getUnSavedDeals();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(unSavedDeals.get(0).getUnSaveReason(), UnSaveReason.DUPLICATE);
    }


    @Test
    public void testPatchFixDeals_AllSavedSuccess() {

        FixDealsResponse expectedResponse = new FixDealsResponse("Deals saved successfully",
                Collections.emptyList(), inputFixDealDtoList);

        when(fixDealsService.saveDeals(inputFixDealDtoList)).thenReturn(expectedResponse);

        ResponseEntity<FixDealsResponse> responseEntity =
                fixDealsController.patchFixDeals(inputFixDealDtoList);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody(), "Response body should not be null");
        List<FixDealDto> savedDeals = responseEntity.getBody().getSavedDeals();
        assertNotNull(savedDeals, "Saved deals should not be null");
        assertEquals(inputFixDealDtoList.size(), savedDeals.size(), "Number of saved deals should match input");
    }
}