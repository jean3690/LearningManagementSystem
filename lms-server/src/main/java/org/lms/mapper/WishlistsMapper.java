package org.lms.mapper;


import org.apache.ibatis.annotations.Param;
import org.lms.Enum.OpreationType;
import org.lms.annotation.AutoFill;
import org.lms.entity.Wishlists;

import java.time.Instant;
import java.util.List;

/**
* @author jeang
* @description 针对表【wishlists(用户课程收藏表)】的数据库操作Mapper
* @createDate 2025-09-13 23:04:45
* @Entity .entity.Wishlists
*/
public interface WishlistsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Wishlists record);
    @AutoFill({OpreationType.CREATEDAT})
    int insertSelective(Wishlists record);

    Wishlists selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Wishlists record);

    int updateByPrimaryKey(Wishlists record);

    List<Wishlists> list(@Param("ids") List<Long> ids);

    List<Wishlists> findAll();

    List<Wishlists> searchByCreatedAt(@Param("createdAt") Instant createdAt);

    void deleteBatch(List<Long> ids);
}
