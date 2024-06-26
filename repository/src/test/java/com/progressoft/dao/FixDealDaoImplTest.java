package com.progressoft.dao;


import com.progressoft.common.dto.FXDealDto;
import com.progressoft.common.exception.DuplicateFxDealException;
import com.progressoft.entity.FXDealEntity;
import com.progressoft.mapper.FXDealEntityMapper;
import com.progressoft.repository.FixDealsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FixDealDaoImplTest {

    @Mock
    private FixDealsRepository fixDealsRepository;

    @Mock
    private FXDealEntityMapper FXDealEntityMapper;

    @InjectMocks
    private FxDealDaoImpl fixDealDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPersist_Success() {
        FXDealDto FXDealDto = new FXDealDto("1", "USD", "EUR", new BigDecimal("100.00"));
        FXDealEntity FXDealEntity = new FXDealEntity();
        when(FXDealEntityMapper.toEntity(FXDealDto)).thenReturn(FXDealEntity);

        doReturn(FXDealEntity).when(fixDealsRepository).save(any());

        fixDealDao.persist(FXDealDto);

        verify(FXDealEntityMapper, times(1)).toEntity(FXDealDto);
        verify(fixDealsRepository, times(1)).save(FXDealEntity);
    }

    @Test
    public void testPersist_DuplicateException() {
        FXDealDto FXDealDto = new FXDealDto("1", "USD", "EUR", new BigDecimal("100.00"));
        when(FXDealEntityMapper.toEntity(FXDealDto)).thenReturn(new FXDealEntity());

        doThrow(DataIntegrityViolationException.class).when(fixDealsRepository).save(any());

        assertThrows(DuplicateFxDealException.class, () ->
                fixDealDao.persist(FXDealDto)
        );

        verify(FXDealEntityMapper, times(1)).toEntity(FXDealDto);
        verify(fixDealsRepository, times(1)).save(any());
    }
}