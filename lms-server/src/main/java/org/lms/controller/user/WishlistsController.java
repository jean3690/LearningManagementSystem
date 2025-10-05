package org.lms.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.lms.dto.WishlistsDto;
import org.lms.response.Result;
import org.lms.service.WishlistsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController("userWishlistsController")
@RequestMapping("/user/whishlists")
@Slf4j
@Tag(name = "用户收藏接口")
public class WishlistsController {
    private WishlistsService wishlistsService;

    public WishlistsController(WishlistsService wishlistsService) {
        this.wishlistsService = wishlistsService;
    }

    @GetMapping("/page/{pageNum}/{pageSize}")
    @Operation(summary = "分页查询")
    public Result page(@PathVariable("pageNum") Integer pageNum,
                       @PathVariable("pageSize") Integer pageSize){
        return wishlistsService.page(pageNum,pageSize);
    }
    @GetMapping("/searchByCreatedAt")
    @Operation(summary = "查询")
    public Result searchByCreatedAt(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Instant createdAt) {
        return wishlistsService.searchByCreatedAt(createdAt);
    }
    @PostMapping("/add")
    @Operation(summary = "添加收藏")
    public Result add(@RequestBody WishlistsDto wishlistsDto){
        return wishlistsService.add(wishlistsDto);
    }
    @DeleteMapping("/delete")
    @Operation(summary = "删除收藏")
    public Result delete(@RequestBody List<Long> ids){
        return wishlistsService.delete(ids);
    }
}
