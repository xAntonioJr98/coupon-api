package com.torres.antonio.coupon_api.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, String> {

    Optional<CouponEntity> findByCode(String code);

    @Query(value = "SELECT * FROM tb_coupon WHERE is_deleted = true", nativeQuery = true)
    List<CouponEntity> findDeletedCoupons();
}