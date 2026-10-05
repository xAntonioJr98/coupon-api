package com.torres.antonio.coupon_api.application.usecase;

import com.torres.antonio.coupon_api.application.port.CouponRepositoryPort;
import com.torres.antonio.coupon_api.domain.exception.DomainException;
import com.torres.antonio.coupon_api.domain.model.CouponDomain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateCouponUseCase {

    private final CouponRepositoryPort repositoryPort;

    public CreateCouponUseCase(CouponRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public CouponDomain execute(String code, String description, BigDecimal discountValue, LocalDate expirationDate, boolean isPublished) {

        CouponDomain newCoupon = new CouponDomain(code, description, discountValue, expirationDate, isPublished);

        if (repositoryPort.findByCode(newCoupon.getCode()).isPresent()) {
            throw new DomainException("Já existe um cupom cadastrado com este código.");
        }

        return repositoryPort.save(newCoupon);
    }
}