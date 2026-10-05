package com.torres.antonio.coupon_api.application.port;

import com.torres.antonio.coupon_api.domain.model.CouponDomain;
import java.util.Optional;

public interface CouponRepositoryPort {

    CouponDomain save(CouponDomain coupon);

    Optional<CouponDomain> findByCode(String code);
}