package org.lms.mapper;


import org.lms.entity.BusinessProfiles;

/**
* @author jeang
* @description 针对表【business_profiles(企业用户详细信息表)】的数据库操作Mapper
* @createDate 2025-09-13 23:04:44
* @Entity .entity.BusinessProfiles
*/
public interface BusinessProfilesMapper {

    int deleteByPrimaryKey(Long id);

    int insert(BusinessProfiles record);

    int insertSelective(BusinessProfiles record);

    BusinessProfiles selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusinessProfiles record);

    int updateByPrimaryKey(BusinessProfiles record);

}
