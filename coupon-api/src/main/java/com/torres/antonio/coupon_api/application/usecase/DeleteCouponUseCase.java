package com.torres.antonio.coupon_api.application.usecase;

import com.torres.antonio.coupon_api.application.port.CouponRepositoryPort;
import com.torres.antonio.coupon_api.domain.exception.DomainException;
import com.torres.antonio.coupon_api.domain.model.CouponDomain;

public class DeleteCouponUseCase {

    private final CouponRepositoryPort repositoryPort;

    public DeleteCouponUseCase(CouponRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public void execute(String code) {

        CouponDomain coupon = repositoryPort.findByCode(code.toUpperCase())
                .orElseThrow(() -> new DomainException("Cupom não encontrado para o código informado."));

        coupon.delete();

        repositoryPort.save(coupon);
    }
}