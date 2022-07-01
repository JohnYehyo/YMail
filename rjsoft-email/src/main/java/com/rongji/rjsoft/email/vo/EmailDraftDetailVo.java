package com.rongji.rjsoft.email.vo;

import com.rongji.rjsoft.email.ao.EmailDraftAo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: 邮件草稿明细
 * @author: JohnYehyo
 * @create: 2022-06-30 14:30:00
 */
@ApiModel(value = "邮件草稿明细视图")
@Data
public class EmailDraftDetailVo implements Serializable {

    private static final long serialVersionUID = 4203554062890851433L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 发件人邮箱地址
     */
    @ApiModelProperty(value = "发件人邮箱地址")
    private String emailFrom;

    /**
     * 发件人
     */
    @ApiModelProperty(value = "发件人")
    private String emailUser;


    /**
     * 收件人邮箱
     */
    @ApiModelProperty(value = "收件人邮箱")
    private String emailTo;

    /**
     * 抄送人邮箱
     */
    @ApiModelProperty(value = "抄送人邮箱")
    private String emailCc;

    /**
     * 密送人邮箱
     */
    @ApiModelProperty(value = "密送人邮箱")
    private String emailBcc;


    /**
     * 主题
     */
    @ApiModelProperty(value = "主题")
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

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
}
