package org.lms.dto;

//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
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
     * 用户手机号码
     */
    private String phone;
    private String token;
}
