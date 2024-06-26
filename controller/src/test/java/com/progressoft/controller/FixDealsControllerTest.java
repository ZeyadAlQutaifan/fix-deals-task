package com.progressoft.controller;

import com.progressoft.common.dto.FixDealDto;
import com.progressoft.common.interactor.FixDealsResponse;
import com.progressoft.common.interactor.UnsavedDeal;
import com.progressoft.common.service.FixDealsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
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

        List<UnsavedDeal> unsavedDeals = Arrays.asList(
                new UnsavedDeal(inputFixDealDtoList.getFirst().getDealId(), "Deal is duplicated")
        );
        inputFixDealDtoList.remove(0);
        FixDealsResponse expectedResponse = new FixDealsResponse("OK",
                unsavedDeals, inputFixDealDtoList);
        ResponseEntity<FixDealsResponse> responseEntity =
                fixDealsController.patchFixDeals(inputFixDealDtoList);
        List<UnsavedDeal> unSavedDeals = responseEntity.getBody().getUnSavedDeals();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(1,savedDeals.getUnSavedDeals().size());

    }


    @Test
    public void testPatchFixDeals_AllSavedSuccess() {
        // Mock data

        FixDealsResponse expectedResponse = new FixDealsResponse("Deals saved successfully",
                Collections.emptyList(), inputFixDealDtoList);

        // Mock service method with specific argument
        when(fixDealsService.saveDeals(inputFixDealDtoList)).thenReturn(expectedResponse);

        // Call controller method
        ResponseEntity<FixDealsResponse> responseEntity =
                fixDealsController.patchFixDeals(inputFixDealDtoList);
        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody(), "Response body should not be null");
        // Check savedDeals size and content
        List<FixDealDto> savedDeals = responseEntity.getBody().getSavedDeals();
        assertNotNull(savedDeals, "Saved deals should not be null");
        assertEquals(inputFixDealDtoList.size(), savedDeals.size(), "Number of saved deals should match input");
        // Add more specific assertions as needed on savedDeals content
    }
}