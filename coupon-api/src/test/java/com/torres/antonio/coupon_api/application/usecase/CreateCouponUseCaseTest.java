package com.torres.antonio.coupon_api.application.usecase;

import com.torres.antonio.coupon_api.application.port.CouponRepositoryPort;
import com.torres.antonio.coupon_api.domain.exception.DomainException;
import com.torres.antonio.coupon_api.domain.model.CouponDomain;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCouponUseCaseTest {

    @Mock
    private CouponRepositoryPort repositoryPort;

    @InjectMocks
    private CreateCouponUseCase useCase;

    @Test
    void deveOrquestrarCriacaoDeCupomComSucesso() {
        String codigoValido = "PROMO1";
        when(repositoryPort.findByCode(codigoValido)).thenReturn(Optional.empty());

        when(repositoryPort.save(any(CouponDomain.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CouponDomain resultado = useCase.execute(codigoValido, "Descrição", new BigDecimal("10.0"), LocalDate.now().plusDays(2), true);

        assertNotNull(resultado);
        assertEquals(codigoValido, resultado.getCode());
        verify(repositoryPort, times(1)).save(any(CouponDomain.class));
    }

    @Test
    void deveLancarExcecaoQuandoCodigoJaExistirNaBaseDeDados() {
        String codigoDuplicado = "PROMO1";
        CouponDomain cupomExistente = new CouponDomain(codigoDuplicado, "Antigo", new BigDecimal("5.0"), LocalDate.now().plusDays(1), true);

        when(repositoryPort.findByCode(codigoDuplicado)).thenReturn(Optional.of(cupomExistente));

        DomainException exception = assertThrows(DomainException.class, () ->
                useCase.execute(codigoDuplicado, "Novo", new BigDecimal("15.0"), LocalDate.now().plusDays(5), true)
        );

        assertEquals("Já existe um cupom cadastrado com este código.", exception.getMessage());
        verify(repositoryPort, never()).save(any(CouponDomain.class));
    }
}