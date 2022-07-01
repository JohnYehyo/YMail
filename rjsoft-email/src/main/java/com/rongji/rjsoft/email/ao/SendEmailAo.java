package com.rongji.rjsoft.email.ao;

import com.rongji.rjsoft.email.annotation.MutiEmail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @description: 发送邮件参数体
 * @author: JohnYehyo
 * @create: 2022-06-30 09:12:06
 */
@ApiModel(value = "发送邮件参数体")
@Data
public class SendEmailAo implements Serializable {

    private static final long serialVersionUID = -2879835972137723766L;

    /**
     * 服务SMTP服务器域名
     */
    @ApiModelProperty(value = "服务地址", hidden = true)
    private String host;

    /**
     * 服务SMTP端口
     */
    @ApiModelProperty(value = "服务SMTP端口", hidden = true)
    private String port;

    /**
     * 是否认证
     */
    @ApiModelProperty(value = "是否认证", hidden = true)
    private boolean auth = true;

    /**
     * 发件人邮箱地址
     */
    @ApiModelProperty(value = "发件人邮箱地址", required = true)
    @Email(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$", message = "请填写正确的发件人邮箱地址")
    private String from;

    /**
     * 发件人
     */
    @ApiModelProperty(value = "发件人", required = true)
    @NotBlank(message = "请填写发件人")
    private String user;


    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", hidden = true)
    private String password;

    /**
     * 收件人邮箱
     */
    @ApiModelProperty(value = "收件人邮箱", required = true)
    @MutiEmail(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$", message = "请填写正确的发件人邮箱地址")
    private String to;

    /**
     * 抄送人邮箱
     */
    @ApiModelProperty(value = "抄送人邮箱")
    @MutiEmail(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$", message = "请填写正确的发件人邮箱地址")
    private String cc;

    /**
     * 密送人邮箱
     */
    @ApiModelProperty(value = "密送人邮箱")
    private String bcc;


    /**
     * 主题
     */
    @ApiModelProperty(value = "主题", required = true)
    @NotBlank(message = "请填写主题")
    private String subject;

    /**
     * 正文
     */
    @ApiModelProperty(value = "正文")
    private String content;

    /**
     * 附件
     */
    @ApiModelProperty(value = "附件")
    private List<String> files;

    /**
     * 邮件内容类型
     */
    @ApiModelProperty(value = "邮件内容类型")
    private int type;
}
