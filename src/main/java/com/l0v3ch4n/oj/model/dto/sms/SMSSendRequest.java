package com.l0v3ch4n.oj.model.dto.sms;

import lombok.Data;

import java.io.Serializable;

/**
 * 邮箱验证码发送请求体
 */
@Data
public class SMSSendRequest implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;
    /**
     * 邮箱地址
     */
    private String userMail;
}
