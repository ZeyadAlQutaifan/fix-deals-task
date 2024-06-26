package com.progressoft.controller;

import com.progressoft.common.dto.FXDealDto;
import com.progressoft.common.interactor.FxDealsResponse;
import com.progressoft.common.interactor.UnSaveReason;
import com.progressoft.common.interactor.UnsavedDeal;
import com.progressoft.common.service.FxDealsService;
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
class FxDealsControllerTest {
    @Mock
    private FxDealsService fxDealsService;

    @InjectMocks
    private FxDealsController fxDealsController;

    List<FXDealDto> inputFXDealDtoList;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        inputFXDealDtoList = new ArrayList<>(Arrays.asList(
                new FXDealDto("1", "USD", "EUR", new BigDecimal("100.00")),
                new FXDealDto("2", "EUR", "USD", new BigDecimal("200.00")),
                new FXDealDto("3", "EUR", "AED", new BigDecimal("30.00")),
                new FXDealDto("4", "JOD", "AED", new BigDecimal("30.00"))
        ));
    }

    @Test
    public void testPatchFXDeals_ContainDuplicate() {

        List<UnsavedDeal> unsavedDeals = List.of(
                new UnsavedDeal(inputFXDealDtoList.get(0).getDealId(), "Deal is duplicated", UnSaveReason.DUPLICATE)
        );
        inputFXDealDtoList.remove(0);
        FxDealsResponse expectedResponse = new FxDealsResponse("OK",
                unsavedDeals, inputFXDealDtoList);
        when(fxDealsService.saveDeals(inputFXDealDtoList)).thenReturn(expectedResponse);

        ResponseEntity<FxDealsResponse> responseEntity =
                fxDealsController.patchFXDeals(inputFXDealDtoList);
        List<UnsavedDeal> unSavedDeals = Objects.requireNonNull(responseEntity.getBody()).getUnSavedDeals();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(unSavedDeals.get(0).getUnSaveReason(), UnSaveReason.DUPLICATE);
    }


    @Test
    public void testPatchFXDeals_AllSavedSuccess() {

        FxDealsResponse expectedResponse = new FxDealsResponse("Deals saved successfully",
                Collections.emptyList(), inputFXDealDtoList);

        when(fxDealsService.saveDeals(inputFXDealDtoList)).thenReturn(expectedResponse);

        ResponseEntity<FxDealsResponse> responseEntity =
                fxDealsController.patchFXDeals(inputFXDealDtoList);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody(), "Response body should not be null");
        List<FXDealDto> savedDeals = responseEntity.getBody().getSavedDeals();
        assertNotNull(savedDeals, "Saved deals should not be null");
        assertEquals(inputFXDealDtoList.size(), savedDeals.size(), "Number of saved deals should match input");
    }
}