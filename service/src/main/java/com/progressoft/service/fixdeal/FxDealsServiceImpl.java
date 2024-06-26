package com.progressoft.service.fixdeal;

import com.progressoft.common.dao.FxDealDAO;
import com.progressoft.common.dto.FXDealDto;
import com.progressoft.common.exception.DuplicateFxDealException;
import com.progressoft.common.exception.InvalidDealException;
import com.progressoft.common.interactor.FxDealsResponse;
import com.progressoft.common.interactor.UnSaveReason;
import com.progressoft.common.interactor.UnsavedDeal;
import com.progressoft.common.service.FxDealsService;
import com.progressoft.validate.DealInputValidator;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@AllArgsConstructor
public class FxDealsServiceImpl implements FxDealsService {
    private final FxDealDAO fxDealDAO;
    private final DealInputValidator dealInputValidator;

    @Override
    public FxDealsResponse saveDeals(List<FXDealDto> deals) {
        List<UnsavedDeal> unSavedFixDeals = new ArrayList<>();
        List<FXDealDto> savedFixDeals = new ArrayList<>();
        for (FXDealDto deal : deals) {
            try {
                dealInputValidator.validateDeal(deal);
                fxDealDAO.persist(deal);
                savedFixDeals.add(deal);
                log.info("Deal successfully inserted: {}", deal.getDealId());
            } catch (InvalidDealException e) {
                unSavedFixDeals.add(new UnsavedDeal(deal.getDealId(), e.getMessage(), UnSaveReason.INVALID));
                log.error("Validation error for deal: {}", deal.getDealId(), e);
            } catch (DuplicateFxDealException e) {
                unSavedFixDeals.add(new UnsavedDeal(deal.getDealId(), "Deal is duplicated", UnSaveReason.DUPLICATE));
                log.error("Deal {}} already exists", deal.getDealId(), e);
            } catch (Exception e) {
                unSavedFixDeals.add(new UnsavedDeal(deal.getDealId(), "Unexpected exception occurs while process deal %s", UnSaveReason.INTERNAL_ERROR));
                log.error("Error inserting deal: {}", deal.getDealId(), e);
            }
        }
        return new FxDealsResponse("OK", unSavedFixDeals, savedFixDeals);
    }
}
