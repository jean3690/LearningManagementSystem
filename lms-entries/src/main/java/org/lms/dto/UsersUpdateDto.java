package org.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersUpdateDto {
    /**
     * 用户唯一标识ID
     */
    private Long id;

    /**
     * 全局唯一标识符，用于外部引用
     */
    private String uuid;

    /**
     * 用户邮箱，用于登录和通知
     */
    private String email;

    /**
     * 加密后的密码
     */
    private String password;

    /**
     * 用户名字
     */
    private String username;

    /**
     * 用户头像URL地址
     */
    private String avatarUrl;

    /**
     * 用户手机号码
     */
    private String phone;

    /**
     * 用户类型：个人用户/企业用户
     */
    private Object userType;

    /**
     * 账户类型：免费/基础/高级/企业
     */
    private Object accountType;

    /**
     * 邮箱是否已验证
     */
    private Integer emailVerified;

    /**
     * 账户是否激活
     */
    private Integer isActive;

}
