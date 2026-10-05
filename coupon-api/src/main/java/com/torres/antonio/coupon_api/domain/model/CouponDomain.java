package com.torres.antonio.coupon_api.domain.model;

import com.torres.antonio.coupon_api.domain.exception.DomainException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class CouponDomain {

    private String id;
    private String code;
    private String description;
    private BigDecimal discountValue;
    private LocalDate expirationDate;
    private boolean isPublished;
    private boolean isDeleted;

    public CouponDomain(String code, String description, BigDecimal discountValue, LocalDate expirationDate, boolean isPublished) {
        this.id = UUID.randomUUID().toString();
        this.description = description;
        this.isPublished = isPublished;
        this.isDeleted = false;

        validateAndSetCode(code);
        validateAndSetDiscountValue(discountValue);
        validateAndSetExpirationDate(expirationDate);
    }

    public CouponDomain(String id, String code, String description, BigDecimal discountValue, LocalDate expirationDate, boolean isPublished, boolean isDeleted) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.discountValue = discountValue;
        this.expirationDate = expirationDate;
        this.isPublished = isPublished;
        this.isDeleted = isDeleted;
    }

    public void delete() {
        if (this.isDeleted) {
            throw new DomainException("Não é possível deletar um cupom que já foi deletado.");
        }
        this.isDeleted = true;
    }

    private void validateAndSetCode(String code) {
        if (code == null || code.isBlank()) {
            throw new DomainException("O código do cupom é obrigatório.");
        }

        String sanitizedCode = code.replaceAll("[^a-zA-Z0-9]", "");

        if (sanitizedCode.length() != 6) {
            throw new DomainException("O código do cupom deve ter exatamente 6 caracteres alfanuméricos válidos.");
        }
        this.code = sanitizedCode.toUpperCase();
    }

    private void validateAndSetDiscountValue(BigDecimal discountValue) {
        if (discountValue == null || discountValue.compareTo(new BigDecimal("0.5")) < 0) {
            throw new DomainException("O valor do desconto deve ser no mínimo 0.5.");
        }
        this.discountValue = discountValue;
    }

    private void validateAndSetExpirationDate(LocalDate expirationDate) {
        if (expirationDate == null || expirationDate.isBefore(LocalDate.now())) {
            throw new DomainException("A data de expiração não pode estar no passado.");
        }
        this.expirationDate = expirationDate;
    }

    public String getId() {
        return id;
    }
    public String getCode() {
        return code;
    }
    public String getDescription() {
        return description;
    }
    public BigDecimal getDiscountValue() {
        return discountValue;
    }
    public LocalDate getExpirationDate() {
        return expirationDate;
    }
    public boolean isPublished() {
        return isPublished;
    }
    public boolean isDeleted() {
        return isDeleted;
    }
}