package org.lms.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程报名表
 * @TableName enrollments
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enrollments {
    /**
     * 报名ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 关联的订单项ID(如果通过购买报名)
     */
    private Long orderItemId;

    /**
     * 报名时间
     */
    private Instant enrolledAt;

    /**
     * 完成时间
     */
    private Instant completedAt;

    /**
     * 完成百分比
     */
    private BigDecimal completionPercentage;

    /**
     * 最后访问时间
     */
    private Instant lastAccessedAt;

    /**
     * 最后学习的课时ID
     */
    private Long lastLessonId;

    /**
     * 是否有效
     */
    private Integer isActive;

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
        Enrollments other = (Enrollments) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getCourseId() == null ? other.getCourseId() == null : this.getCourseId().equals(other.getCourseId()))
            && (this.getOrderItemId() == null ? other.getOrderItemId() == null : this.getOrderItemId().equals(other.getOrderItemId()))
            && (this.getEnrolledAt() == null ? other.getEnrolledAt() == null : this.getEnrolledAt().equals(other.getEnrolledAt()))
            && (this.getCompletedAt() == null ? other.getCompletedAt() == null : this.getCompletedAt().equals(other.getCompletedAt()))
            && (this.getCompletionPercentage() == null ? other.getCompletionPercentage() == null : this.getCompletionPercentage().equals(other.getCompletionPercentage()))
            && (this.getLastAccessedAt() == null ? other.getLastAccessedAt() == null : this.getLastAccessedAt().equals(other.getLastAccessedAt()))
            && (this.getLastLessonId() == null ? other.getLastLessonId() == null : this.getLastLessonId().equals(other.getLastLessonId()))
            && (this.getIsActive() == null ? other.getIsActive() == null : this.getIsActive().equals(other.getIsActive()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getCourseId() == null) ? 0 : getCourseId().hashCode());
        result = prime * result + ((getOrderItemId() == null) ? 0 : getOrderItemId().hashCode());
        result = prime * result + ((getEnrolledAt() == null) ? 0 : getEnrolledAt().hashCode());
        result = prime * result + ((getCompletedAt() == null) ? 0 : getCompletedAt().hashCode());
        result = prime * result + ((getCompletionPercentage() == null) ? 0 : getCompletionPercentage().hashCode());
        result = prime * result + ((getLastAccessedAt() == null) ? 0 : getLastAccessedAt().hashCode());
        result = prime * result + ((getLastLessonId() == null) ? 0 : getLastLessonId().hashCode());
        result = prime * result + ((getIsActive() == null) ? 0 : getIsActive().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", courseId=").append(courseId);
        sb.append(", orderItemId=").append(orderItemId);
        sb.append(", enrolledAt=").append(enrolledAt);
        sb.append(", completedAt=").append(completedAt);
        sb.append(", completionPercentage=").append(completionPercentage);
        sb.append(", lastAccessedAt=").append(lastAccessedAt);
        sb.append(", lastLessonId=").append(lastLessonId);
        sb.append(", isActive=").append(isActive);
        sb.append("]");
        return sb.toString();
    }
}