package com.torres.antonio.coupon_api.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_coupon")
@SQLDelete(sql = "UPDATE tb_coupon SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted = false")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponEntity {

    @Id
    private String id;

    @Column(unique = true, length = 6, nullable = false)
    private String code;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal discountValue;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @Column(nullable = false)
    private boolean isPublished;

    @Column(nullable = false)
    private boolean isDeleted = false;
}
