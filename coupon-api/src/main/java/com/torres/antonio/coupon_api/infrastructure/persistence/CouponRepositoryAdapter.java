package com.torres.antonio.coupon_api.infrastructure.persistence;

import com.torres.antonio.coupon_api.application.port.CouponRepositoryPort;
import com.torres.antonio.coupon_api.domain.model.CouponDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CouponRepositoryAdapter implements CouponRepositoryPort {

    private final CouponRepository jpaRepository;

    @Override
    public CouponDomain save(CouponDomain coupon) {

        CouponEntity entity = new CouponEntity(
                coupon.getId(),
                coupon.getCode(),
                coupon.getDescription(),
                coupon.getDiscountValue(),
                coupon.getExpirationDate(),
                coupon.isPublished(),
                coupon.isDeleted()
        );

        CouponEntity savedEntity = jpaRepository.save(entity);

        return mapToDomain(savedEntity);
    }

    @Override
    public Optional<CouponDomain> findByCode(String code) {
        return jpaRepository.findByCode(code)
                .map(this::mapToDomain);
    }

    private CouponDomain mapToDomain(CouponEntity entity) {
        return new CouponDomain(
                entity.getId(),
                entity.getCode(),
                entity.getDescription(),
                entity.getDiscountValue(),
                entity.getExpirationDate(),
                entity.isPublished(),
                entity.isDeleted()
        );
    }
}