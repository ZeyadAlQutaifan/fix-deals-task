package com.progressoft.controller;

import com.progressoft.common.dto.FXDealDto;
import com.progressoft.common.interactor.FxDealsResponse;
import com.progressoft.common.service.FxDealsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/public/api")
public class FxDealsController {
    final FxDealsService fxDealsService;

    public FxDealsController(FxDealsService fxDealsService) {
        this.fxDealsService = fxDealsService;
    }


    /**
     * TODO | IMPORTANT |
     * in the requirement of the task it does not mention what to do with the unsaved records
     * I will save the record that can be saved, and the record which contains problems
     * will be retrieved to the client and mention the reason why this record did not saved
     */
    @PostMapping("/save/deal")
    public ResponseEntity<FxDealsResponse> patchFXDeals(@RequestBody List<FXDealDto> FXDealDtoList) {
        FxDealsResponse fxDealsResponseEntity = fxDealsService.saveDeals(FXDealDtoList);
        return ResponseEntity.status(OK).body(fxDealsResponseEntity);
    }
}
