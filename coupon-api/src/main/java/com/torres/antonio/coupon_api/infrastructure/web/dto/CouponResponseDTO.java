package com.torres.antonio.coupon_api.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CouponResponseDTO(
        String id,
        String code,
        String description,
        BigDecimal discountValue,
        LocalDate expirationDate,
        boolean published,
        boolean deleted
) {}