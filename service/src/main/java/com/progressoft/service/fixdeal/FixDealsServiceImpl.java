package com.progressoft.service.fixdeal;

import com.progressoft.common.dao.FixDealDAO;
import com.progressoft.common.dto.FixDealDto;
import com.progressoft.common.exception.DuplicateFixDealException;
import com.progressoft.common.exception.InvalidDealException;
import com.progressoft.common.interactor.FixDealsResponse;
import com.progressoft.common.interactor.UnsavedDeal;
import com.progressoft.common.service.FixDealsService;
import com.progressoft.validate.DealInputValidator;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@AllArgsConstructor
public class FixDealsServiceImpl implements FixDealsService {
    private final FixDealDAO fixDealDAO;
    private final DealInputValidator dealInputValidator;

    @Override
    public FixDealsResponse saveDeals(List<FixDealDto> deals) {
        List<UnsavedDeal> unSavedFixDeals = new ArrayList<>();
        List<FixDealDto> savedFixDeals = new ArrayList<>();
        for (FixDealDto deal : deals) {
            try {
                dealInputValidator.validateDeal(deal);
                fixDealDAO.persist(deal);
                savedFixDeals.add(deal);
                log.info("Deal successfully inserted: {}", deal.getDealId());
            } catch (InvalidDealException e) {
                unSavedFixDeals.add(new UnsavedDeal(deal.getDealId() , e.getMessage()));
                log.error("Validation error for deal: {}", deal.getDealId(), e);
            } catch (DuplicateFixDealException e) {
                unSavedFixDeals.add(new UnsavedDeal( deal.getDealId() , "Deal is duplicated"));
                log.error("Deal {}} already exists", deal.getDealId(), e);
            } catch (Exception e) {
                unSavedFixDeals.add(new UnsavedDeal(deal.getDealId() , "Unexpected exception occurs while process deal %s"));
                log.error("Error inserting deal: {}", deal.getDealId(), e);
            }
        }
        return new FixDealsResponse("OK", unSavedFixDeals, savedFixDeals);
    }
}
