package org.lms.entity;

import java.util.Date;
import lombok.Data;

/**
 * 用户基本信息表
 * @TableName users
 */
@Data
public class Users {
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
    private String firstName;

    /**
     * 用户姓氏
     */
    private String lastName;

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

    /**
     * 最后登录时间
     */
    private Date lastLoginAt;

    /**
     * 账户创建时间
     */
    private Date createdAt;

    /**
     * 最后更新时间
     */
    private Date updatedAt;

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
        Users other = (Users) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUuid() == null ? other.getUuid() == null : this.getUuid().equals(other.getUuid()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getFirstName() == null ? other.getFirstName() == null : this.getFirstName().equals(other.getFirstName()))
            && (this.getLastName() == null ? other.getLastName() == null : this.getLastName().equals(other.getLastName()))
            && (this.getAvatarUrl() == null ? other.getAvatarUrl() == null : this.getAvatarUrl().equals(other.getAvatarUrl()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getUserType() == null ? other.getUserType() == null : this.getUserType().equals(other.getUserType()))
            && (this.getAccountType() == null ? other.getAccountType() == null : this.getAccountType().equals(other.getAccountType()))
            && (this.getEmailVerified() == null ? other.getEmailVerified() == null : this.getEmailVerified().equals(other.getEmailVerified()))
            && (this.getIsActive() == null ? other.getIsActive() == null : this.getIsActive().equals(other.getIsActive()))
            && (this.getLastLoginAt() == null ? other.getLastLoginAt() == null : this.getLastLoginAt().equals(other.getLastLoginAt()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUuid() == null) ? 0 : getUuid().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getFirstName() == null) ? 0 : getFirstName().hashCode());
        result = prime * result + ((getLastName() == null) ? 0 : getLastName().hashCode());
        result = prime * result + ((getAvatarUrl() == null) ? 0 : getAvatarUrl().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getUserType() == null) ? 0 : getUserType().hashCode());
        result = prime * result + ((getAccountType() == null) ? 0 : getAccountType().hashCode());
        result = prime * result + ((getEmailVerified() == null) ? 0 : getEmailVerified().hashCode());
        result = prime * result + ((getIsActive() == null) ? 0 : getIsActive().hashCode());
        result = prime * result + ((getLastLoginAt() == null) ? 0 : getLastLoginAt().hashCode());
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
        sb.append(", uuid=").append(uuid);
        sb.append(", email=").append(email);
        sb.append(", password=").append(password);
        sb.append(", firstName=").append(firstName);
        sb.append(", lastName=").append(lastName);
        sb.append(", avatarUrl=").append(avatarUrl);
        sb.append(", phone=").append(phone);
        sb.append(", userType=").append(userType);
        sb.append(", accountType=").append(accountType);
        sb.append(", emailVerified=").append(emailVerified);
        sb.append(", isActive=").append(isActive);
        sb.append(", lastLoginAt=").append(lastLoginAt);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append("]");
        return sb.toString();
    }
}