package org.lms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程基本信息表
 * @TableName courses
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "课程信息")
public class Courses implements Serializable {
    /**
     * 课程ID
     */
    private Long id;

    /**
     * 课程标题
     */
    private String title;

    /**
     * 课程副标题
     */
    private String subtitle;

    /**
     * 课程详细描述
     */
    private String description;

    /**
     * 讲师用户ID
     */
    private Long instructorId;

    /**
     * 课程分类ID
     */
    private Integer categoryId;

    /**
     * 课程难度级别
     */
    private Object level;

    /**
     * 课程语言
     */
    private String language;

    /**
     * 课程缩略图URL
     */
    private String thumbnailUrl;

    /**
     * 课程预览视频URL
     */
    private String videoPreviewUrl;

    /**
     * 课程总时长(小时)
     */
    private Integer durationHours;

    /**
     * 是否为草稿状态
     */
    private Integer isDraft;

    /**
     * 是否已审核通过
     */
    private Integer isApproved;

    /**
     * 审核通过日期
     */
    private Instant approvalDate;

    /**
     * 课程平均评分
     */
    private BigDecimal rating;

    /**
     * 总评分人数
     */
    private Integer totalRatings;

    /**
     * 总报名人数
     */
    private Integer totalEnrollments;

    /**
     * 课程价格
     */
    private BigDecimal price;

    /**
     * 折扣价格
     */
    private BigDecimal discountPrice;

    /**
     * 是否免费课程
     */
    private Integer isFree;

    /**
     * 是否颁发证书
     */
    private Integer willIssueCertificate;

    /**
     * 学习要求
     */
    private String requirements;

    /**
     * 学习成果
     */
    private String learningOutcomes;

    /**
     * 目标受众
     */
    private String targetAudience;

    /**
     * 创建时间
     */
    private Instant createdAt;

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
        Courses other = (Courses) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getSubtitle() == null ? other.getSubtitle() == null : this.getSubtitle().equals(other.getSubtitle()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getInstructorId() == null ? other.getInstructorId() == null : this.getInstructorId().equals(other.getInstructorId()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
            && (this.getLanguage() == null ? other.getLanguage() == null : this.getLanguage().equals(other.getLanguage()))
            && (this.getThumbnailUrl() == null ? other.getThumbnailUrl() == null : this.getThumbnailUrl().equals(other.getThumbnailUrl()))
            && (this.getVideoPreviewUrl() == null ? other.getVideoPreviewUrl() == null : this.getVideoPreviewUrl().equals(other.getVideoPreviewUrl()))
            && (this.getDurationHours() == null ? other.getDurationHours() == null : this.getDurationHours().equals(other.getDurationHours()))
            && (this.getIsDraft() == null ? other.getIsDraft() == null : this.getIsDraft().equals(other.getIsDraft()))
            && (this.getIsApproved() == null ? other.getIsApproved() == null : this.getIsApproved().equals(other.getIsApproved()))
            && (this.getApprovalDate() == null ? other.getApprovalDate() == null : this.getApprovalDate().equals(other.getApprovalDate()))
            && (this.getRating() == null ? other.getRating() == null : this.getRating().equals(other.getRating()))
            && (this.getTotalRatings() == null ? other.getTotalRatings() == null : this.getTotalRatings().equals(other.getTotalRatings()))
            && (this.getTotalEnrollments() == null ? other.getTotalEnrollments() == null : this.getTotalEnrollments().equals(other.getTotalEnrollments()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getDiscountPrice() == null ? other.getDiscountPrice() == null : this.getDiscountPrice().equals(other.getDiscountPrice()))
            && (this.getIsFree() == null ? other.getIsFree() == null : this.getIsFree().equals(other.getIsFree()))
            && (this.getWillIssueCertificate() == null ? other.getWillIssueCertificate() == null : this.getWillIssueCertificate().equals(other.getWillIssueCertificate()))
            && (this.getRequirements() == null ? other.getRequirements() == null : this.getRequirements().equals(other.getRequirements()))
            && (this.getLearningOutcomes() == null ? other.getLearningOutcomes() == null : this.getLearningOutcomes().equals(other.getLearningOutcomes()))
            && (this.getTargetAudience() == null ? other.getTargetAudience() == null : this.getTargetAudience().equals(other.getTargetAudience()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getSubtitle() == null) ? 0 : getSubtitle().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getInstructorId() == null) ? 0 : getInstructorId().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
        result = prime * result + ((getLanguage() == null) ? 0 : getLanguage().hashCode());
        result = prime * result + ((getThumbnailUrl() == null) ? 0 : getThumbnailUrl().hashCode());
        result = prime * result + ((getVideoPreviewUrl() == null) ? 0 : getVideoPreviewUrl().hashCode());
        result = prime * result + ((getDurationHours() == null) ? 0 : getDurationHours().hashCode());
        result = prime * result + ((getIsDraft() == null) ? 0 : getIsDraft().hashCode());
        result = prime * result + ((getIsApproved() == null) ? 0 : getIsApproved().hashCode());
        result = prime * result + ((getApprovalDate() == null) ? 0 : getApprovalDate().hashCode());
        result = prime * result + ((getRating() == null) ? 0 : getRating().hashCode());
        result = prime * result + ((getTotalRatings() == null) ? 0 : getTotalRatings().hashCode());
        result = prime * result + ((getTotalEnrollments() == null) ? 0 : getTotalEnrollments().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getDiscountPrice() == null) ? 0 : getDiscountPrice().hashCode());
        result = prime * result + ((getIsFree() == null) ? 0 : getIsFree().hashCode());
        result = prime * result + ((getWillIssueCertificate() == null) ? 0 : getWillIssueCertificate().hashCode());
        result = prime * result + ((getRequirements() == null) ? 0 : getRequirements().hashCode());
        result = prime * result + ((getLearningOutcomes() == null) ? 0 : getLearningOutcomes().hashCode());
        result = prime * result + ((getTargetAudience() == null) ? 0 : getTargetAudience().hashCode());
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
        sb.append(", title=").append(title);
        sb.append(", subtitle=").append(subtitle);
        sb.append(", description=").append(description);
        sb.append(", instructorId=").append(instructorId);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", level=").append(level);
        sb.append(", language=").append(language);
        sb.append(", thumbnailUrl=").append(thumbnailUrl);
        sb.append(", videoPreviewUrl=").append(videoPreviewUrl);
        sb.append(", durationHours=").append(durationHours);
        sb.append(", isDraft=").append(isDraft);
        sb.append(", isApproved=").append(isApproved);
        sb.append(", approvalDate=").append(approvalDate);
        sb.append(", rating=").append(rating);
        sb.append(", totalRatings=").append(totalRatings);
        sb.append(", totalEnrollments=").append(totalEnrollments);
        sb.append(", price=").append(price);
        sb.append(", discountPrice=").append(discountPrice);
        sb.append(", isFree=").append(isFree);
        sb.append(", willIssueCertificate=").append(willIssueCertificate);
        sb.append(", requirements=").append(requirements);
        sb.append(", learningOutcomes=").append(learningOutcomes);
        sb.append(", targetAudience=").append(targetAudience);
        sb.append(", createdAt=").append(createdAt);
        sb.append("]");
        return sb.toString();
    }
}