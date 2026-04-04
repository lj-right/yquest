package com.suifeng.yquest.controller;

import com.suifeng.yquest.api.common.Result;
import com.suifeng.yquest.entity.ShareLike;
import com.suifeng.yquest.service.ShareLikeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (ShareLike)点赞表控制层
 */
@RestController
@RequestMapping("shareLike")
public class ShareLikeController {
    /**
     * 服务对象
     */
    @Resource
    private ShareLikeService shareLikeService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result<ShareLike> queryById(@PathVariable("id") Long id) {
        return Result.ok(this.shareLikeService.queryById(id));
    }

    /**
     * 通过momentId查询单条数据
     */
    @PostMapping("/getByMomentId")
    public Result<ShareLike> getByMomentId(@RequestBody ShareLike shareLike) {
        return Result.ok(this.shareLikeService.getByMomentId(shareLike));
    }

    /**
     * 通过commentId查询单条数据
     */
    @PostMapping("/getByCommentId")
    public Result<ShareLike> getByCommentId(@RequestBody ShareLike shareLike) {
        return Result.ok(this.shareLikeService.getByCommentId(shareLike));
    }

    /**
     * 查询评论/回复是否点赞
     */
    @PostMapping("/isLiked")
    public Result<Boolean> isLiked(@RequestBody ShareLike shareLike) {
        return Result.ok(this.shareLikeService.isLiked(shareLike));
    }

    /**
     * 点赞：新增数据
     *
     * @param shareLike 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody ShareLike shareLike) {
        return Result.ok(this.shareLikeService.insert(shareLike));
    }

    /**
     * 获取点赞数量
     *
     */
    @PostMapping("/getAccount")
    public Result<Integer> getAccount(@RequestBody ShareLike shareLike) {
        return Result.ok(this.shareLikeService.getAccount(shareLike));
    }

    /**
     * 编辑数据
     *
     * @param shareLike 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public Result<Boolean> edit(@RequestBody ShareLike shareLike) {
        return Result.ok(this.shareLikeService.update(shareLike));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/deleteById")
    public Result<Boolean> deleteById(Long id) {
        return Result.ok(this.shareLikeService.deleteById(id));
    }

}

