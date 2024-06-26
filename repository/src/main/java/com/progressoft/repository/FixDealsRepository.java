package com.progressoft.repository;

import com.progressoft.entity.FixDealEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixDealsRepository extends JpaRepository<FixDealEntity, Long> {

}

