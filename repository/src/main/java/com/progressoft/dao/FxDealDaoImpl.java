package com.progressoft.dao;

import com.progressoft.common.dao.FxDealDAO;
import com.progressoft.common.dto.FXDealDto;
import com.progressoft.common.exception.DuplicateFxDealException;
import com.progressoft.entity.FXDealEntity;
import com.progressoft.mapper.FXDealEntityMapper;
import com.progressoft.repository.FixDealsRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class FxDealDaoImpl implements FxDealDAO {
    private final FixDealsRepository fixDealsRepository;
    private final FXDealEntityMapper FXDealEntityMapper;

    public FxDealDaoImpl(FixDealsRepository fixDealsRepository, FXDealEntityMapper FXDealEntityMapper) {
        this.fixDealsRepository = fixDealsRepository;
        this.FXDealEntityMapper = FXDealEntityMapper;
    }

    @Override
    public void persist(FXDealDto FXDealDto) {
        try {
            FXDealEntity entity = FXDealEntityMapper.toEntity(FXDealDto);
            fixDealsRepository.save(entity);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateFxDealException(ex.getMessage(), ex);
        }
    }
}
