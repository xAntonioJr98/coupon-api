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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCouponUseCaseTest {

    @Mock
    private CouponRepositoryPort repositoryPort;

    @InjectMocks
    private DeleteCouponUseCase useCase;

    @Test
    void deveOrquestrarDelecaoDeCupomComSucesso() {
        String codigo = "PROMO1";
        CouponDomain cupomExistente = new CouponDomain(codigo, "Para deletar", new BigDecimal("10.0"), LocalDate.now().plusDays(2), true);

        when(repositoryPort.findByCode(codigo)).thenReturn(Optional.of(cupomExistente));

        useCase.execute(codigo);

        assertTrue(cupomExistente.isDeleted());
        verify(repositoryPort, times(1)).save(cupomExistente);
    }

    @Test
    void deveLancarExcecaoQuandoTentarDeletarCupomInexistente() {
        String codigoInexistente = "FALSO1";
        when(repositoryPort.findByCode(codigoInexistente)).thenReturn(Optional.empty());

        DomainException exception = assertThrows(DomainException.class, () -> useCase.execute(codigoInexistente));

        assertEquals("Cupom não encontrado para o código informado.", exception.getMessage());
        verify(repositoryPort, never()).save(any(CouponDomain.class));
    }
}