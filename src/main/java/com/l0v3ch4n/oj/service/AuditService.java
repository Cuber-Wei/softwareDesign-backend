package com.l0v3ch4n.oj.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.l0v3ch4n.oj.model.entity.Post;
import com.l0v3ch4n.oj.model.entity.PostComment;
import com.l0v3ch4n.oj.model.entity.WriteUp;
import com.l0v3ch4n.oj.model.enums.AuditTypeEnum;
import com.l0v3ch4n.oj.model.enums.ReviewStatusEnum;

import javax.servlet.http.HttpServletRequest;

public interface AuditService {
    /**
     * 分页获取待审核的评论列表
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    Page<PostComment> listCommentPostByPage(Page<PostComment> page, Wrapper<PostComment> queryWrapper);

    /**
     * 分页获取待审核的帖子列表
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    Page<Post> listPostByPage(Page<Post> page, Wrapper<Post> queryWrapper);

    /**
     * 分页获取待审核的题解列表
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    Page<WriteUp> listWriteUpByPage(Page<WriteUp> page, Wrapper<WriteUp> queryWrapper);

    /**
     * 审核行为
     *
     * @param id        操作对象id
     * @param type      操作对象类型
     * @param operation 操作结果状态
     * @param request   操作请求
     * @return
     */
    int doAudit(Long id, AuditTypeEnum type, ReviewStatusEnum operation, HttpServletRequest request);
}
