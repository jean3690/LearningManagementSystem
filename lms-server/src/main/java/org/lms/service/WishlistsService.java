package org.lms.service;


import org.lms.dto.WishlistsDto;
import org.lms.response.Result;

import java.time.Instant;
import java.util.List;

/**
* @author jeang
* @description 针对表【wishlists(用户课程收藏表)】的数据库操作Service
* @createDate 2025-09-13 22:53:03
*/
public interface WishlistsService  {

    Result page(Integer pageNum, Integer pageSize);

    Result searchByCreatedAt(Instant createdAt);

    Result add(WishlistsDto wishlistsDto);

    Result delete(List<Long> ids);
}
