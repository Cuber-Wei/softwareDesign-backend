package com.l0v3ch4n.oj.service.impl;

import com.l0v3ch4n.oj.common.ErrorCode;
import com.l0v3ch4n.oj.exception.BusinessException;
import com.l0v3ch4n.oj.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import java.util.Date;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendText(String from, String to, String subject, String content) {
        if (StringUtils.isAnyBlank(from, to, subject, content)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        try {
            //true表示支持复杂类型
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
            //邮件发信人
            messageHelper.setFrom(new InternetAddress("OJ平台 <" + from + ">"));
            //邮件收信人
            messageHelper.setTo(to.split(","));
            //邮件主题
            messageHelper.setSubject(subject);
            //邮件内容
            messageHelper.setText(content, false);
            // 邮件发送时间
            messageHelper.setSentDate(new Date());
            //正式发送邮件
            mailSender.send(messageHelper.getMimeMessage());
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
    }
}
