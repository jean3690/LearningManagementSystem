package org.lms.mapper;


import org.lms.entity.Notifications;

/**
* @author jeang
* @description 针对表【notifications(系统通知表)】的数据库操作Mapper
* @createDate 2025-09-13 23:04:45
* @Entity .entity.Notifications
*/
public interface NotificationsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Notifications record);

    int insertSelective(Notifications record);

    Notifications selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Notifications record);

    int updateByPrimaryKey(Notifications record);

}
