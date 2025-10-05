package org.lms.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发票表
 * @TableName invoices
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "发票")
public class InvoicesDto implements Serializable {
    /**
     * 发票ID
     */
    private Long id;

    /**
     * 发票号码
     */
    private String invoiceNumber;

    /**
     * 关联的订单ID
     */
    private Long orderId;

    /**
     * 用户ID
     */
    @NotNull
    private Long userId;

    /**
     * 开票日期
     */
    private Instant issueDate;

    /**
     * 到期日期
     */
    private Instant dueDate;

    /**
     * 发票金额
     */
    @NotNull
    private BigDecimal amount;

    /**
     * 税费金额
     */
    @NotNull
    private BigDecimal taxAmount;

    /**
     * 发票总金额
     */
    @NotNull
    private BigDecimal totalAmount;

    /**
     * 发票状态
     */
    private Object status;

    /**
     * 支付日期
     */
    private Instant paymentDate;

}