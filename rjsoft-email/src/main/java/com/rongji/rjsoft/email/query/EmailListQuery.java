package com.rongji.rjsoft.email.query;

import com.rongji.rjsoft.query.common.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import java.io.Serializable;

/**
 * @description: 邮件列表查询
 * @author: JohnYehyo
 * @create: 2022-06-30 09:12:06
 */
@ApiModel(value = "邮件列表查询")
@Data
public class EmailListQuery extends PageQuery implements Serializable {

    private static final long serialVersionUID = -3795937119815676611L;

    /**
     * 服务地址
     */
    @ApiModelProperty(value = "服务地址", hidden = true)
    private String host;

    /**
     * 服务SMTP端口
     */
    @ApiModelProperty(value = "服务SMTP端口", hidden = true)
    private String port;


    /**
     * 发件人邮箱地址
     */
    @ApiModelProperty(value = "发件人邮箱地址")
    @Email(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$", message = "请填写正确的发件人邮箱地址")
    private String from;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", hidden = true)
    private String password;

    /**
     * 收件人邮箱
     */
    @ApiModelProperty(value = "收件人邮箱")
    @Email(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$", message = "请填写正确的收件人邮箱地址")
    private String to;


    /**
     * 主题
     */
    @ApiModelProperty(value = "主题")
    private String subject;

    /**
     * 协议类型 POP3 IMAP
     */
    @ApiModelProperty(value = "协议类型")
    private String protocol;

    /**
     * 邮件内容类型
     */
    @ApiModelProperty(value = "邮件内容类型")
    private int type;

    /**
     * 邮箱类型
     */
    @ApiModelProperty(value = "邮箱类型")
    private String box;
}
