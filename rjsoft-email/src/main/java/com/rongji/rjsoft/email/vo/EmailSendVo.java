package com.rongji.rjsoft.email.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description: 已发送邮件
 * @author: JohnYehyo
 * @create: 2022-06-30 14:28:53
 */
@ApiModel(value = "已发送邮件视图")
@Data
public class EmailSendVo {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "收件人邮箱")
    private String emailTo;

    @ApiModelProperty(value = "抄送人邮箱")
    private String emailCc;

    @ApiModelProperty(value = "主题")
    private String emailSubject;

    @ApiModelProperty(value = "邮件类型")
    private Integer emailType;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
