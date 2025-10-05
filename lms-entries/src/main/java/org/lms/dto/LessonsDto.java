package org.lms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lms.entity.LessonProgress;

import java.io.Serializable;
import java.time.Instant;

/**
 * 课程课时内容表
 * @TableName lessons
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "课程时间内容")
public class LessonsDto implements Serializable {
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

    private LessonProgress lessonProgress;
}