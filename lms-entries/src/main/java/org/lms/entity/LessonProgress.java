package org.lms.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课时学习进度表
 * @TableName lesson_progress
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "课程进度")
public class LessonProgress implements Serializable {
    /**
     * 进度ID
     */
    private Long id;

    /**
     * 报名ID
     */
    private Long enrollmentId;

    /**
     * 课时ID
     */
    private Long lessonId;

    /**
     * 学习状态
     */
    private Object status;

    /**
     * 进度百分比
     */
    private Integer progressPercentage;

    /**
     * 最后播放位置(秒，针对视频)
     */
    private Integer lastPosition;

    /**
     * 开始学习时间
     */
    private Instant startedAt;

    /**
     * 完成学习时间
     */
    private Instant completedAt;

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
        LessonProgress other = (LessonProgress) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getEnrollmentId() == null ? other.getEnrollmentId() == null : this.getEnrollmentId().equals(other.getEnrollmentId()))
            && (this.getLessonId() == null ? other.getLessonId() == null : this.getLessonId().equals(other.getLessonId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getProgressPercentage() == null ? other.getProgressPercentage() == null : this.getProgressPercentage().equals(other.getProgressPercentage()))
            && (this.getLastPosition() == null ? other.getLastPosition() == null : this.getLastPosition().equals(other.getLastPosition()))
            && (this.getStartedAt() == null ? other.getStartedAt() == null : this.getStartedAt().equals(other.getStartedAt()))
            && (this.getCompletedAt() == null ? other.getCompletedAt() == null : this.getCompletedAt().equals(other.getCompletedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getEnrollmentId() == null) ? 0 : getEnrollmentId().hashCode());
        result = prime * result + ((getLessonId() == null) ? 0 : getLessonId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getProgressPercentage() == null) ? 0 : getProgressPercentage().hashCode());
        result = prime * result + ((getLastPosition() == null) ? 0 : getLastPosition().hashCode());
        result = prime * result + ((getStartedAt() == null) ? 0 : getStartedAt().hashCode());
        result = prime * result + ((getCompletedAt() == null) ? 0 : getCompletedAt().hashCode());
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
        sb.append(", enrollmentId=").append(enrollmentId);
        sb.append(", lessonId=").append(lessonId);
        sb.append(", status=").append(status);
        sb.append(", progressPercentage=").append(progressPercentage);
        sb.append(", lastPosition=").append(lastPosition);
        sb.append(", startedAt=").append(startedAt);
        sb.append(", completedAt=").append(completedAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append("]");
        return sb.toString();
    }
}