package org.lms.entity;

import java.util.Date;
import lombok.Data;

/**
 * 系统通知表
 * @TableName notifications
 */
@Data
public class Notifications {
    /**
     * 通知ID
     */
    private Long id;

    /**
     * 接收用户ID
     */
    private Long userId;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知内容
     */
    private String message;

    /**
     * 通知类型
     */
    private Object type;

    /**
     * 是否已读
     */
    private Integer isRead;

    /**
     * 关联实体类型
     */
    private String relatedEntityType;

    /**
     * 关联实体ID
     */
    private Long relatedEntityId;

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
        Notifications other = (Notifications) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getMessage() == null ? other.getMessage() == null : this.getMessage().equals(other.getMessage()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getIsRead() == null ? other.getIsRead() == null : this.getIsRead().equals(other.getIsRead()))
            && (this.getRelatedEntityType() == null ? other.getRelatedEntityType() == null : this.getRelatedEntityType().equals(other.getRelatedEntityType()))
            && (this.getRelatedEntityId() == null ? other.getRelatedEntityId() == null : this.getRelatedEntityId().equals(other.getRelatedEntityId()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getMessage() == null) ? 0 : getMessage().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getIsRead() == null) ? 0 : getIsRead().hashCode());
        result = prime * result + ((getRelatedEntityType() == null) ? 0 : getRelatedEntityType().hashCode());
        result = prime * result + ((getRelatedEntityId() == null) ? 0 : getRelatedEntityId().hashCode());
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
        sb.append(", userId=").append(userId);
        sb.append(", title=").append(title);
        sb.append(", message=").append(message);
        sb.append(", type=").append(type);
        sb.append(", isRead=").append(isRead);
        sb.append(", relatedEntityType=").append(relatedEntityType);
        sb.append(", relatedEntityId=").append(relatedEntityId);
        sb.append(", createdAt=").append(createdAt);
        sb.append("]");
        return sb.toString();
    }
}