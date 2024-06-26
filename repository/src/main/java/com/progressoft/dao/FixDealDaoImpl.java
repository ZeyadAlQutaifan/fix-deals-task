package com.progressoft.dao;

import com.progressoft.common.dao.FixDealDAO;
import com.progressoft.common.dto.FixDealDto;
import com.progressoft.common.exception.DuplicateFixDealException;
import com.progressoft.entity.FixDealEntity;
import com.progressoft.mapper.FixDealEntityMapper;
import com.progressoft.repository.FixDealsRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class FixDealDaoImpl implements FixDealDAO {
    private final FixDealsRepository fixDealsRepository;
    private final FixDealEntityMapper fixDealEntityMapper;

    public FixDealDaoImpl(FixDealsRepository fixDealsRepository, FixDealEntityMapper fixDealEntityMapper) {
        this.fixDealsRepository = fixDealsRepository;
        this.fixDealEntityMapper = fixDealEntityMapper;
    }

    @Override
    public void persist(FixDealDto fixDealDto) {
        try {
            FixDealEntity entity = fixDealEntityMapper.toEntity(fixDealDto);
            fixDealsRepository.save(entity);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateFixDealException(ex.getMessage(), ex);
        }
    }
}
