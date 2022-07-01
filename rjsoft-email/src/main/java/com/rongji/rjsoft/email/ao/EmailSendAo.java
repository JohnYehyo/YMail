package com.rongji.rjsoft.email.ao;

import com.rongji.rjsoft.email.annotation.MutiEmail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @description: 发件箱参数体
 * @author: JohnYehyo
 * @create: 2022-06-30 09:12:06
 */
@ApiModel(value = "发件箱参数体")
@Data
public class EmailSendAo implements Serializable {

    private static final long serialVersionUID = -2879835972137723766L;

    public interface save {
    }

    public interface edit {
    }


    /**
     * 主键
     */
    @ApiModelProperty(value = "主键id")
    @NotNull(
            groups = {edit.class},
            message = "主键id不能为空"
    )
    private Long id;

    /**
     * 服务SMTP服务器域名
     */
    @ApiModelProperty(value = "服务地址", hidden = true)
    private String emailHost;

    /**
     * 服务SMTP端口
     */
    @ApiModelProperty(value = "服务SMTP端口", hidden = true)
    private String emailPort;

    /**
     * 是否认证
     */
    @ApiModelProperty(value = "是否认证", hidden = true)
    private boolean emailAuth = true;

    /**
     * 发件人邮箱地址
     */
    @ApiModelProperty(value = "发件人邮箱地址", required = true)
    @Email(
            groups = {save.class, edit.class},
            regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$", message = "请填写正确的发件人邮箱地址"
    )
    private String emailFrom;

    /**
     * 发件人
     */
    @ApiModelProperty(value = "发件人", required = true)
    @NotBlank(groups = {save.class, edit.class}, message = "请填写发件人")
    private String emailUser;


    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", hidden = true)
    private String emailPassword;

    /**
     * 收件人邮箱
     */
    @ApiModelProperty(value = "收件人邮箱", required = true)
    @MutiEmail(
            groups = {save.class, edit.class},
            regexp = "(^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$)|(^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}(;|$))$", message = "请填写正确的收件人邮箱地址"
    )
    private String emailTo;

    /**
     * 抄送人邮箱
     */
    @ApiModelProperty(value = "抄送人邮箱")
    @MutiEmail(
            groups = {save.class, edit.class},
            regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$", message = "请填写正确的抄送人邮箱地址"
    )
    private String emailCc;

    /**
     * 密送人邮箱
     */
    @ApiModelProperty(value = "密送人邮箱")
    @MutiEmail(
            groups = {save.class, edit.class},
            regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$", message = "请填写正确的密送人邮箱地址"
    )
    private String emailBcc;


    /**
     * 主题
     */
    @ApiModelProperty(value = "主题", required = true)
    @NotBlank(message = "请填写主题")
    private String emailSubject;

    /**
     * 正文
     */
    @ApiModelProperty(value = "正文")
    private String emailContent;

    /**
     * 附件
     */
    @ApiModelProperty(value = "附件")
    private List<String> emailFiles;

    /**
     * 邮件内容类型
     */
    @ApiModelProperty(value = "邮件内容类型")
    private int emailType;

}
