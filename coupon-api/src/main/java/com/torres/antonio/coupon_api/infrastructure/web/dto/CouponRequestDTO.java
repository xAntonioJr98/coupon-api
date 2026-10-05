package com.torres.antonio.coupon_api.infrastructure.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CouponRequestDTO(
        @NotBlank(message = "O código é obrigatório")
        String code,

        @NotBlank(message = "A descrição é obrigatória")
        String description,

        @NotNull(message = "O valor do desconto é obrigatório")
        @DecimalMin(value = "0.5", message = "O desconto mínimo deve ser 0.5")
        BigDecimal discountValue,

        @NotNull(message = "A data de expiração é obrigatória")
        @FutureOrPresent(message = "A data de expiração não pode estar no passado")
        LocalDate expirationDate,

        @NotNull(message = "O status de publicação é obrigatório")
        Boolean published
) {}