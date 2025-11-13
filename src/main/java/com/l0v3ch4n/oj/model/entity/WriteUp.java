package com.l0v3ch4n.oj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 题解
 *
 * @TableName write_up
 */
@TableName(value = "write_up")
@Data
public class WriteUp implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long writeUpId;
    /**
     * 创建用户 id
     */
    private Long userId;
    /**
     * 题目 id
     */
    private Long questionId;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 标签列表（json 数组）
     */
    private String tag;
    /**
     * 审核状态（0 - 待审核、1 - 审核通过、2 - 审核未通过）
     */
    private Integer reviewStatus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否删除
     */
    private Integer isDelete;
}