package org.lms.dto;

//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * 用户课程收藏表
 * @TableName wishlists
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Schema(name = "收藏")
public class WishlistsDto {
    /**
     * 收藏ID
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
}