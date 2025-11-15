package com.l0v3ch4n.oj.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.l0v3ch4n.oj.model.dto.postcomment.PostCommentQueryRequest;
import com.l0v3ch4n.oj.model.entity.Post;
import com.l0v3ch4n.oj.model.entity.PostComment;
import com.l0v3ch4n.oj.model.entity.User;

/**
 * @author weichenghao
 * @description 针对表【post_comment(帖子评论)】的数据库操作Service
 * @createDate 2025-11-14 20:31:03
 */
public interface PostCommentService extends IService<PostComment> {
    /**
     * 帖子评论
     *
     * @param postId
     * @param loginUser
     * @return
     */
    int doPostComment(long postId, User loginUser, String comment);

    /**
     * 分页获取用户评论的帖子列表
     *
     * @param page
     * @param queryWrapper
     * @param commentUserId
     * @return
     */
    Page<Post> listCommentPostByPage(IPage<Post> page, Wrapper<Post> queryWrapper,
                                     long commentUserId);

    /**
     * 删除用户评论
     *
     * @param postId
     * @param commentId
     * @return
     */
    int deletePostComment(long postId, long commentId, long userId);

    /**
     * 帖子评论（内部服务）
     *
     * @param userId
     * @param postId
     * @return
     */
    int doPostCommentInner(long userId, long postId, String comment);

    /**
     * 获取查询条件
     *
     * @param postCommentQueryRequest
     * @return
     */
    QueryWrapper<PostComment> getQueryWrapper(PostCommentQueryRequest postCommentQueryRequest);
}
