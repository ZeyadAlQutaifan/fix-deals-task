package com.progressoft.dao;


import com.progressoft.common.dto.FixDealDto;
import com.progressoft.common.exception.DuplicateFixDealException;
import com.progressoft.entity.FixDealEntity;
import com.progressoft.mapper.FixDealEntityMapper;
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
    private FixDealEntityMapper fixDealEntityMapper;

    @InjectMocks
    private FixDealDaoImpl fixDealDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPersist_Success() {
        FixDealDto fixDealDto = new FixDealDto("1", "USD", "EUR", new BigDecimal("100.00"));
        FixDealEntity fixDealEntity = new FixDealEntity();
        when(fixDealEntityMapper.toEntity(fixDealDto)).thenReturn(fixDealEntity);

        doReturn(fixDealEntity).when(fixDealsRepository).save(any());

        fixDealDao.persist(fixDealDto);

        verify(fixDealEntityMapper, times(1)).toEntity(fixDealDto);
        verify(fixDealsRepository, times(1)).save(fixDealEntity);
    }

    @Test
    public void testPersist_DuplicateException() {
        FixDealDto fixDealDto = new FixDealDto("1", "USD", "EUR", new BigDecimal("100.00"));
        when(fixDealEntityMapper.toEntity(fixDealDto)).thenReturn(new FixDealEntity());

        doThrow(DataIntegrityViolationException.class).when(fixDealsRepository).save(any());

        assertThrows(DuplicateFixDealException.class, () ->
                fixDealDao.persist(fixDealDto)
        );

        verify(fixDealEntityMapper, times(1)).toEntity(fixDealDto);
        verify(fixDealsRepository, times(1)).save(any());
    }
}