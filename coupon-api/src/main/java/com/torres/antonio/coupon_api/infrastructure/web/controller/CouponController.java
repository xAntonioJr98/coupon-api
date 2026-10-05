package com.torres.antonio.coupon_api.infrastructure.web.controller;

import com.torres.antonio.coupon_api.application.usecase.CreateCouponUseCase;
import com.torres.antonio.coupon_api.application.usecase.DeleteCouponUseCase;
import com.torres.antonio.coupon_api.domain.model.CouponDomain;
import com.torres.antonio.coupon_api.infrastructure.web.dto.CouponRequestDTO;
import com.torres.antonio.coupon_api.infrastructure.web.dto.CouponResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
@Tag(name = "Coupon API", description = "Endpoints para gerenciamento de cupons de desconto")
public class CouponController {

    private final CreateCouponUseCase createCouponUseCase;
    private final DeleteCouponUseCase deleteCouponUseCase;

    @PostMapping
    @Operation(summary = "Criar um novo cupom", description = "Cadastra um novo cupom aplicando regras de domínio e sanitização de código.")
    @ApiResponse(responseCode = "201", description = "Cupom criado com sucesso")
    @ApiResponse(responseCode = "422", description = "Erro de regra de negócio")
    public ResponseEntity<CouponResponseDTO> create(@RequestBody @Valid CouponRequestDTO request) {
        CouponDomain coupon = createCouponUseCase.execute(
                request.code(),
                request.description(),
                request.discountValue(),
                request.expirationDate(),
                request.published()
        );

        CouponResponseDTO response = new CouponResponseDTO(
                coupon.getId(),
                coupon.getCode(),
                coupon.getDescription(),
                coupon.getDiscountValue(),
                coupon.getExpirationDate(),
                coupon.isPublished(),
                coupon.isDeleted()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{code}")
    @Operation(summary = "Deletar um cupom (Soft Delete)", description = "Realiza a exclusão lógica de um cupom com base no código informado.")
    @ApiResponse(responseCode = "204", description = "Cupom deletado com sucesso")
    @ApiResponse(responseCode = "422", description = "Cupom não encontrado ou já deletado")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        deleteCouponUseCase.execute(code);
        return ResponseEntity.noContent().build();
    }
}