package com.progressoft.controller;

import com.progressoft.common.dto.FixDealDto;
import com.progressoft.common.interactor.FixDealsResponse;
import com.progressoft.common.service.FixDealsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/public/api")
public class FixDealsController {
    final FixDealsService fixDealsService;

    public FixDealsController(FixDealsService fixDealsService) {
        this.fixDealsService = fixDealsService;
    }


    /**
     * TODO | IMPORTANT |
     * in the requirement of the task it does not mention what to do with the unsaved records
     * I will save the record that can be saved, and the record which contains problems
     * will be retrieved to the client and mention the reason why this record did not saved
     * @param fixDealDtoList
     * @return
     */
    @PostMapping("/save/deal")
    public ResponseEntity<FixDealsResponse> patchFixDeals(@RequestBody List<FixDealDto> fixDealDtoList) {
        FixDealsResponse fixDealsResponseEntity = fixDealsService.saveDeals(fixDealDtoList);
        return ResponseEntity.status(OK).body(fixDealsResponseEntity);
    }
}
