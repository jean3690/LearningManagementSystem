package org.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDto {
    /**
     * 订单ID
     */
    private Long id;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 下单用户ID
     */
    private Long userId;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 折扣金额
     */
    private BigDecimal discountAmount;

    /**
     * 税费金额
     */
    private BigDecimal taxAmount;

    /**
     * 最终支付金额
     */
    private BigDecimal finalAmount;

    /**
     * 使用的优惠券ID
     */
    private Long couponId;

    /**
     * 订单状态
     */
    private Object status;

    /**
     * 支付方式
     */
    private Object paymentMethod;

    /**
     * 支付网关返回的支付ID
     */
    private String paymentId;

    /**
     * 账单地址
     */
    private String billingAddress;

    /**
     * 创建时间
     */
    private Instant createdAt;

    /**
     * 最后更新时间
     */
    private Instant updatedAt;
}
