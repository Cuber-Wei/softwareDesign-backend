package com.l0v3ch4n.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.l0v3ch4n.oj.constant.AuditConstant;
import com.l0v3ch4n.oj.model.entity.Post;
import com.l0v3ch4n.oj.model.entity.PostComment;
import com.l0v3ch4n.oj.model.entity.WriteUp;
import com.l0v3ch4n.oj.model.enums.AuditTypeEnum;
import com.l0v3ch4n.oj.model.enums.ReviewStatusEnum;
import com.l0v3ch4n.oj.service.AuditService;
import com.l0v3ch4n.oj.service.PostCommentService;
import com.l0v3ch4n.oj.service.PostService;
import com.l0v3ch4n.oj.service.WriteUpService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class AuditServiceImpl implements AuditService {

    @Resource
    private PostCommentService postCommentService;
    @Resource
    private PostService postService;
    @Resource
    private WriteUpService writeUpService;

    /**
     * 分页获取待审核的评论列表
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    public Page<PostComment> listCommentPostByPage(Page<PostComment> page, Wrapper<PostComment> queryWrapper) {
        return postCommentService.page(page, queryWrapper);
    }

    /**
     * 分页获取待审核的帖子列表
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    public Page<Post> listPostByPage(Page<Post> page, Wrapper<Post> queryWrapper) {
        return postService.page(page, queryWrapper);
    }

    /**
     * 分页获取待审核的题解列表
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    public Page<WriteUp> listWriteUpByPage(Page<WriteUp> page, Wrapper<WriteUp> queryWrapper) {
        return writeUpService.page(page, queryWrapper);
    }

    /**
     * 审核行为
     *
     * @param id        操作对象id
     * @param type      操作对象类型
     * @param operation 操作结果状态
     * @param request   操作请求
     * @return
     */
    public int doAudit(Long id, AuditTypeEnum type, ReviewStatusEnum operation, HttpServletRequest request) {
        boolean result;
        if (AuditTypeEnum.POST.equals(type)) {
            Post post = postService.getById(id);
            if (ReviewStatusEnum.PASSED.equals(operation)) {
                post.setReviewStatus(AuditConstant.PASSED);
            } else if (ReviewStatusEnum.RETURNED.equals(operation)) {
                post.setReviewStatus(AuditConstant.FAILED);
            }
            result = postService.updateById(post);
        } else if (AuditTypeEnum.COMMENT.equals(type)) {
            PostComment postComment = postCommentService.getById(id);
            if (ReviewStatusEnum.PASSED.equals(operation)) {
                postComment.setReviewStatus(AuditConstant.PASSED);
            } else if (ReviewStatusEnum.RETURNED.equals(operation)) {
                postComment.setReviewStatus(AuditConstant.FAILED);
            }
            result = postCommentService.updateById(postComment);
        } else {
            WriteUp writeUp = writeUpService.getById(id);
            if (ReviewStatusEnum.PASSED.equals(operation)) {
                writeUp.setReviewStatus(AuditConstant.PASSED);
            } else if (ReviewStatusEnum.RETURNED.equals(operation)) {
                writeUp.setReviewStatus(AuditConstant.FAILED);
            }
            result = writeUpService.updateById(writeUp);
        }
        return result ? 1 : 0;
    }

}
