package org.lms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

/**
 * 课程模块/章节表
 * @TableName course_modules
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "课程模块")
public class CourseModulesDto implements Serializable {
    /**
     * 模块ID
     */
    private Long id;

    /**
     * 所属课程ID
     */
    private Long courseId;

    /**
     * 模块标题
     */
    private String title;

    /**
     * 模块描述
     */
    private String description;

    /**
     * 排序序号
     */
    private Integer sortOrder;

}