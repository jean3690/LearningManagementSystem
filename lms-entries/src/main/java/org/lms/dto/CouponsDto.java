package org.lms.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 优惠券表
 * @TableName coupons
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "优惠卷")
public class CouponsDto implements Serializable {
    /**
     * 优惠券ID
     */
    private Long id;

    /**
     * 优惠券代码
     */
    private String code;

    /**
     * 优惠券描述
     */
    private String description;

    /**
     * 折扣类型:百分比/固定金额
     */
    private Object discountType;

    /**
     * 折扣值
     */
    private BigDecimal discountValue;

    /**
     * 最大折扣金额(对百分比折扣有效)
     */
    private BigDecimal maxDiscountAmount;

    /**
     * 最低消费金额
     */
    private BigDecimal minPurchaseAmount;

    /**
     * 使用次数限制
     */
    private Integer usageLimit;

    /**
     * 已使用次数
     */
    private Integer usedCount;

    /**
     * 有效期开始时间
     */
    private Instant validFrom;

    /**
     * 有效期结束时间
     */
    private Instant validUntil;

    /**
     * 是否有效
     */
    private Integer isActive;
}