package org.lms.entity;

import java.time.Instant;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程课时内容表
 * @TableName lessons
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lessons {
    /**
     * 课时ID
     */
    private Long id;

    /**
     * 所属模块ID
     */
    private Long moduleId;

    /**
     * 课时标题
     */
    private String title;

    /**
     * 内容类型
     */
    private Object contentType;

    /**
     * 课时内容(文本内容或HTML)
     */
    private String content;

    /**
     * 视频URL(如果是视频课时)
     */
    private String videoUrl;

    /**
     * 视频时长(秒)
     */
    private Integer videoDuration;

    /**
     * 附件URL
     */
    private String attachmentUrl;

    /**
     * 排序序号
     */
    private Integer sortOrder;

    /**
     * 是否允许预览
     */
    private Integer isPreviewAvailable;

    /**
     * 创建时间
     */
    private Instant createdAt;

    /**
     * 最后更新时间
     */
    private Instant updatedAt;

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
        Lessons other = (Lessons) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getModuleId() == null ? other.getModuleId() == null : this.getModuleId().equals(other.getModuleId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getContentType() == null ? other.getContentType() == null : this.getContentType().equals(other.getContentType()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getVideoUrl() == null ? other.getVideoUrl() == null : this.getVideoUrl().equals(other.getVideoUrl()))
            && (this.getVideoDuration() == null ? other.getVideoDuration() == null : this.getVideoDuration().equals(other.getVideoDuration()))
            && (this.getAttachmentUrl() == null ? other.getAttachmentUrl() == null : this.getAttachmentUrl().equals(other.getAttachmentUrl()))
            && (this.getSortOrder() == null ? other.getSortOrder() == null : this.getSortOrder().equals(other.getSortOrder()))
            && (this.getIsPreviewAvailable() == null ? other.getIsPreviewAvailable() == null : this.getIsPreviewAvailable().equals(other.getIsPreviewAvailable()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getModuleId() == null) ? 0 : getModuleId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getContentType() == null) ? 0 : getContentType().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getVideoUrl() == null) ? 0 : getVideoUrl().hashCode());
        result = prime * result + ((getVideoDuration() == null) ? 0 : getVideoDuration().hashCode());
        result = prime * result + ((getAttachmentUrl() == null) ? 0 : getAttachmentUrl().hashCode());
        result = prime * result + ((getSortOrder() == null) ? 0 : getSortOrder().hashCode());
        result = prime * result + ((getIsPreviewAvailable() == null) ? 0 : getIsPreviewAvailable().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", moduleId=").append(moduleId);
        sb.append(", title=").append(title);
        sb.append(", contentType=").append(contentType);
        sb.append(", content=").append(content);
        sb.append(", videoUrl=").append(videoUrl);
        sb.append(", videoDuration=").append(videoDuration);
        sb.append(", attachmentUrl=").append(attachmentUrl);
        sb.append(", sortOrder=").append(sortOrder);
        sb.append(", isPreviewAvailable=").append(isPreviewAvailable);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append("]");
        return sb.toString();
    }
}