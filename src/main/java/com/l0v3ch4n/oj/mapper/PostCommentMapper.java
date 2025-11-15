package com.l0v3ch4n.oj.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.l0v3ch4n.oj.model.entity.Post;
import com.l0v3ch4n.oj.model.entity.PostComment;
import org.apache.ibatis.annotations.Param;

/**
 * @author weichenghao
 * @description 针对表【post_comment(帖子评论)】的数据库操作Mapper
 * @createDate 2025-11-14 20:31:03
 * @Entity com.l0v3ch4n.oj.model/entity.PostComment
 */
public interface PostCommentMapper extends BaseMapper<PostComment> {
    /**
     * 分页查询评论帖子列表
     *
     * @param page
     * @param queryWrapper
     * @param commentUserId
     * @return
     */
    Page<Post> listCommentPostByPage(IPage<Post> page, @Param(Constants.WRAPPER) Wrapper<Post> queryWrapper, long commentUserId);
}




