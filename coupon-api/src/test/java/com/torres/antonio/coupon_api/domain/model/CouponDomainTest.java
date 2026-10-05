package com.torres.antonio.coupon_api.domain.model;

import com.torres.antonio.coupon_api.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CouponDomainTest {

    @Test
    void deveCriarCupomComSucesso() {

        CouponDomain coupon = new CouponDomain("PROMO1", "Cupom de Teste", new BigDecimal("10.0"), LocalDate.now().plusDays(5), true);

        assertNotNull(coupon.getId());
        assertEquals("PROMO1", coupon.getCode());
        assertEquals(new BigDecimal("10.0"), coupon.getDiscountValue());
        assertTrue(coupon.isPublished());
        assertFalse(coupon.isDeleted());
    }

    @Test
    void deveLimparCaracteresEspeciaisDoCodigo() {

        CouponDomain coupon = new CouponDomain("P@RO-MO1", "Cupom com caracteres especiais", new BigDecimal("5.0"), LocalDate.now().plusDays(1), false);

        assertEquals("PROMO1", coupon.getCode());
    }

    @Test
    void deveLancarExcecaoQuandoCodigoNaoTiver6Caracteres() {

        DomainException exception = assertThrows(DomainException.class, () -> {
            new CouponDomain("CURTO", "Cupom Curto", new BigDecimal("5.0"), LocalDate.now().plusDays(1), true);
        });
        assertEquals("O código do cupom deve ter exatamente 6 caracteres alfanuméricos válidos.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoDescontoForMenorQueMeio() {

        DomainException exception = assertThrows(DomainException.class, () -> {
            new CouponDomain("VALIDO", "Desconto Baixo", new BigDecimal("0.4"), LocalDate.now().plusDays(1), true);
        });
        assertEquals("O valor do desconto deve ser no mínimo 0.5.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoDataExpiracaoForNoPassado() {

        DomainException exception = assertThrows(DomainException.class, () -> {
            new CouponDomain("VALIDO", "Data Passada", new BigDecimal("5.0"), LocalDate.now().minusDays(1), true);
        });
        assertEquals("A data de expiração não pode estar no passado.", exception.getMessage());
    }

    @Test
    void deveDeletarCupomComSucesso() {
        CouponDomain coupon = new CouponDomain("VALIDO", "Cupom para deletar", new BigDecimal("5.0"), LocalDate.now().plusDays(1), true);

        coupon.delete();

        assertTrue(coupon.isDeleted());
    }

    @Test
    void deveLancarExcecaoAoTentarDeletarCupomJaDeletado() {
        CouponDomain coupon = new CouponDomain("VALIDO", "Cupom deletado duas vezes", new BigDecimal("5.0"), LocalDate.now().plusDays(1), true);
        coupon.delete();

        DomainException exception = assertThrows(DomainException.class, coupon::delete);
        assertEquals("Não é possível deletar um cupom que já foi deletado.", exception.getMessage());
    }
}