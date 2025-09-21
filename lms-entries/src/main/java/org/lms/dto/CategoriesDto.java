package org.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesDto {
    /**
     * 分类ID
     */
    private Integer id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 父分类ID，用于实现多级分类
     */
    private Integer parentId;

    /**
     * 排序序号
     */
    private Integer sortOrder;

    /**
     * 是否激活
     */
    private Integer isActive;
}
