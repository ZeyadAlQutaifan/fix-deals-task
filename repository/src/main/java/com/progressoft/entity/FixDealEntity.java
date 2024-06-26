package com.progressoft.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "FIX_DEALS")
@Getter
@Setter
public class FixDealEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, name = "deal_id", nullable = false)
    private String dealId;

    @Column(name = "from_currency_iso", nullable = false)
    private String fromCurrencyIsoCode;

    @Column(name = "to_currency_iso", nullable = false)
    private String toCurrencyIsoCode;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime dealTimestamp;

    @Column(name = "amount", nullable = false)
    private BigDecimal dealAmount;

}
