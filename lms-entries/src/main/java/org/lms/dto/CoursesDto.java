package org.lms.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

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
public class CoursesDto implements Serializable {
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


}