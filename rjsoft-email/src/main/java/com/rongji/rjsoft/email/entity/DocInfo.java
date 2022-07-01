package com.rongji.rjsoft.email.entity;

import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: JohnYehyo
 * @create: 2022-06-29 13:41:25
 */
@Data
public class DocInfo {

    /**
     * 邮箱消息号
     */
    private String emailCode;

    /**
     * 附件目录
     */
    private String fileDir;

    /**
     * 附件相对路径
     */
    private String filePath;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 发送人
     */
    private String senderEmail;

    private String senderName;

    private Date receiveTime;

    private String receiveMode;

    private Date createTime;

    private String createUser;
}
