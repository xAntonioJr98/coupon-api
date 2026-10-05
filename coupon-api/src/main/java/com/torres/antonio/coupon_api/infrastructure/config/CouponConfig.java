package com.torres.antonio.coupon_api.infrastructure.config;

import com.torres.antonio.coupon_api.application.port.CouponRepositoryPort;
import com.torres.antonio.coupon_api.application.usecase.CreateCouponUseCase;
import com.torres.antonio.coupon_api.application.usecase.DeleteCouponUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CouponConfig {

    @Bean
    public CreateCouponUseCase createCouponUseCase(CouponRepositoryPort repositoryPort) {
        return new CreateCouponUseCase(repositoryPort);
    }

    @Bean
    public DeleteCouponUseCase deleteCouponUseCase(CouponRepositoryPort repositoryPort) {
        return new DeleteCouponUseCase(repositoryPort);
    }
}