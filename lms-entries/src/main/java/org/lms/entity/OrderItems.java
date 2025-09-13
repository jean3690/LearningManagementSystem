package org.lms.entity;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 订单项表
 * @TableName order_items
 */
@Data
public class OrderItems {
    /**
     * 订单项ID
     */
    private Long id;

    /**
     * 所属订单ID
     */
    private Long orderId;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 商品单价
     */
    private BigDecimal unitPrice;

    /**
     * 折扣金额
     */
    private BigDecimal discountAmount;

    /**
     * 最终价格
     */
    private BigDecimal finalPrice;

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
        OrderItems other = (OrderItems) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getCourseId() == null ? other.getCourseId() == null : this.getCourseId().equals(other.getCourseId()))
            && (this.getUnitPrice() == null ? other.getUnitPrice() == null : this.getUnitPrice().equals(other.getUnitPrice()))
            && (this.getDiscountAmount() == null ? other.getDiscountAmount() == null : this.getDiscountAmount().equals(other.getDiscountAmount()))
            && (this.getFinalPrice() == null ? other.getFinalPrice() == null : this.getFinalPrice().equals(other.getFinalPrice()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getCourseId() == null) ? 0 : getCourseId().hashCode());
        result = prime * result + ((getUnitPrice() == null) ? 0 : getUnitPrice().hashCode());
        result = prime * result + ((getDiscountAmount() == null) ? 0 : getDiscountAmount().hashCode());
        result = prime * result + ((getFinalPrice() == null) ? 0 : getFinalPrice().hashCode());
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
        sb.append(", orderId=").append(orderId);
        sb.append(", courseId=").append(courseId);
        sb.append(", unitPrice=").append(unitPrice);
        sb.append(", discountAmount=").append(discountAmount);
        sb.append(", finalPrice=").append(finalPrice);
        sb.append(", createdAt=").append(createdAt);
        sb.append("]");
        return sb.toString();
    }
}