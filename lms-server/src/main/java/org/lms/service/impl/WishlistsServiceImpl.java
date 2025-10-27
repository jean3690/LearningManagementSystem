package org.lms.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lms.dto.WishlistsDto;
import org.lms.entity.Wishlists;
import org.lms.mapper.WishlistsMapper;
import org.lms.response.Result;
import org.lms.service.WishlistsService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
* @author jeang
* @description 针对表【wishlists(用户课程收藏表)】的数据库操作Service实现
* @createDate 2025-09-13 22:53:03
*/
@Service
public class WishlistsServiceImpl  implements WishlistsService {

    private final WishlistsMapper wishlistsMapper;
    private final StringRedisTemplate stringRedisTemplate;

    public WishlistsServiceImpl(WishlistsMapper wishlistsMapper, StringRedisTemplate stringRedisTemplate) {
        this.wishlistsMapper = wishlistsMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Result page(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Wishlists> wishlistsList = wishlistsMapper.findAll();
        return Result.success(new PageInfo<>(wishlistsList));
    }

    @Override
    public Result searchByCreatedAt(Instant createdAt) {
        List<Wishlists> wishlistsList = wishlistsMapper.searchByCreatedAt(createdAt);
        return Result.success(wishlistsList);
    }

    @Override
    public Result add(WishlistsDto wishlistsDto) {
        Wishlists wishlists = new Wishlists();
        BeanUtils.copyProperties(wishlistsDto,wishlists);
        wishlistsMapper.insertSelective(wishlists);
        return Result.success();
    }

    @Override
    public Result delete(List<Long> ids) {
        wishlistsMapper.deleteBatch(ids);
        return Result.success();
    }
}




