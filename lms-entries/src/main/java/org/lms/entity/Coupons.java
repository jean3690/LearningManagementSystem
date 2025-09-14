package org.lms.entity;

import java.math.BigDecimal;
import java.util.Date;

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
public class Coupons {
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
    private Date validFrom;

    /**
     * 有效期结束时间
     */
    private Date validUntil;

    /**
     * 是否有效
     */
    private Integer isActive;

    /**
     * 创建时间
     */
    private Date createdAt;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Coupons other = (Coupons) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getDiscountType() == null ? other.getDiscountType() == null : this.getDiscountType().equals(other.getDiscountType()))
            && (this.getDiscountValue() == null ? other.getDiscountValue() == null : this.getDiscountValue().equals(other.getDiscountValue()))
            && (this.getMaxDiscountAmount() == null ? other.getMaxDiscountAmount() == null : this.getMaxDiscountAmount().equals(other.getMaxDiscountAmount()))
            && (this.getMinPurchaseAmount() == null ? other.getMinPurchaseAmount() == null : this.getMinPurchaseAmount().equals(other.getMinPurchaseAmount()))
            && (this.getUsageLimit() == null ? other.getUsageLimit() == null : this.getUsageLimit().equals(other.getUsageLimit()))
            && (this.getUsedCount() == null ? other.getUsedCount() == null : this.getUsedCount().equals(other.getUsedCount()))
            && (this.getValidFrom() == null ? other.getValidFrom() == null : this.getValidFrom().equals(other.getValidFrom()))
            && (this.getValidUntil() == null ? other.getValidUntil() == null : this.getValidUntil().equals(other.getValidUntil()))
            && (this.getIsActive() == null ? other.getIsActive() == null : this.getIsActive().equals(other.getIsActive()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getDiscountType() == null) ? 0 : getDiscountType().hashCode());
        result = prime * result + ((getDiscountValue() == null) ? 0 : getDiscountValue().hashCode());
        result = prime * result + ((getMaxDiscountAmount() == null) ? 0 : getMaxDiscountAmount().hashCode());
        result = prime * result + ((getMinPurchaseAmount() == null) ? 0 : getMinPurchaseAmount().hashCode());
        result = prime * result + ((getUsageLimit() == null) ? 0 : getUsageLimit().hashCode());
        result = prime * result + ((getUsedCount() == null) ? 0 : getUsedCount().hashCode());
        result = prime * result + ((getValidFrom() == null) ? 0 : getValidFrom().hashCode());
        result = prime * result + ((getValidUntil() == null) ? 0 : getValidUntil().hashCode());
        result = prime * result + ((getIsActive() == null) ? 0 : getIsActive().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", code=").append(code);
        sb.append(", description=").append(description);
        sb.append(", discountType=").append(discountType);
        sb.append(", discountValue=").append(discountValue);
        sb.append(", maxDiscountAmount=").append(maxDiscountAmount);
        sb.append(", minPurchaseAmount=").append(minPurchaseAmount);
        sb.append(", usageLimit=").append(usageLimit);
        sb.append(", usedCount=").append(usedCount);
        sb.append(", validFrom=").append(validFrom);
        sb.append(", validUntil=").append(validUntil);
        sb.append(", isActive=").append(isActive);
        sb.append(", createdAt=").append(createdAt);
        sb.append("]");
        return sb.toString();
    }
}