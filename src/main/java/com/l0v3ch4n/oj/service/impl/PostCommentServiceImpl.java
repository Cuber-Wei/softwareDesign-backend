package com.l0v3ch4n.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.l0v3ch4n.oj.common.ErrorCode;
import com.l0v3ch4n.oj.constant.AuditConstant;
import com.l0v3ch4n.oj.constant.CommonConstant;
import com.l0v3ch4n.oj.exception.BusinessException;
import com.l0v3ch4n.oj.mapper.PostCommentMapper;
import com.l0v3ch4n.oj.model.dto.postcomment.PostCommentQueryRequest;
import com.l0v3ch4n.oj.model.entity.Post;
import com.l0v3ch4n.oj.model.entity.PostComment;
import com.l0v3ch4n.oj.model.entity.User;
import com.l0v3ch4n.oj.service.PostCommentService;
import com.l0v3ch4n.oj.service.PostService;
import com.l0v3ch4n.oj.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author weichenghao
 * @description 针对表【post_comment(帖子评论)】的数据库操作Service实现
 * @createDate 2025-11-14 20:31:03
 */
@Service
public class PostCommentServiceImpl extends ServiceImpl<PostCommentMapper, PostComment>
        implements PostCommentService {
    @Resource
    private PostService postService;

    /**
     * 帖子评论
     *
     * @param postId
     * @param loginUser
     * @param comment
     * @return
     */
    @Override
    public int doPostComment(long postId, User loginUser, String comment) {
        // 判断是否存在
        Post post = postService.getById(postId);
        if (post == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已帖子评论
        long userId = loginUser.getUserId();
        // 每个用户串行帖子评论
        // 锁必须要包裹住事务方法
        PostCommentService postCommentService = (PostCommentService) AopContext.currentProxy();
        synchronized (String.valueOf(userId).intern()) {
            return postCommentService.doPostCommentInner(userId, postId, comment);
        }
    }

    @Override
    public Page<Post> listCommentPostByPage(IPage<Post> page, Wrapper<Post> queryWrapper, long CommentUserId) {
        if (CommentUserId <= 0) {
            return new Page<>();
        }
        return baseMapper.listCommentPostByPage(page, queryWrapper, CommentUserId);
    }

    @Override
    public int deletePostComment(long postId, long commentId, long userId) {
        if (commentId <= 0 || postId <= 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        PostComment postComment = new PostComment();
        postComment.setUserId(userId);
        postComment.setPostId(postId);
        postComment.setPostCommentId(commentId);
        QueryWrapper<PostComment> postCommentQueryWrapper = new QueryWrapper<>(postComment);
        PostComment oldPostComment = this.getOne(postCommentQueryWrapper);
        boolean result;
        if (oldPostComment == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        result = this.remove(postCommentQueryWrapper);
        if (result) {
            return 1;
        } else {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
    }

    /**
     * 封装了事务的方法
     *
     * @param userId
     * @param postId
     * @param comment
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int doPostCommentInner(long userId, long postId, String comment) {
        PostComment postComment = new PostComment();
        postComment.setUserId(userId);
        postComment.setPostId(postId);
        postComment.setContent(comment);
        postComment.setReviewStatus(AuditConstant.PENDING);
        boolean result = this.save(postComment);
        if (result) {
            return 1;
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 获取查询包装类
     *
     * @param postCommentQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<PostComment> getQueryWrapper(PostCommentQueryRequest postCommentQueryRequest) {
        QueryWrapper<PostComment> queryWrapper = new QueryWrapper<>();
        if (postCommentQueryRequest == null) {
            return queryWrapper;
        }
        String sortField = postCommentQueryRequest.getSortField();
        String sortOrder = postCommentQueryRequest.getSortOrder();
        String content = postCommentQueryRequest.getContent();
        Long id = postCommentQueryRequest.getPostCommentId();
        Long postId = postCommentQueryRequest.getPostId();
        Long userId = postCommentQueryRequest.getUserId();

        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(postId), "postId", postId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }
}




