package com.rongji.rjsoft.email.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: 邮件列表返回
 * @author: JohnYehyo
 * @create: 2022-06-30 09:45:34
 */
@ApiModel(value = "邮件列表返回")
@Data
public class EmailListVo implements Serializable {

    private static final long serialVersionUID = 8634248458966187809L;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid")
    private Long uid;

    /**
     * 邮件id
     */
    @ApiModelProperty(value = "邮件id")
    private String emailId;

    /**
     * 发件人
     */
    @ApiModelProperty(value = "发件人")
    private String sender;

    /**
     * 发件人
     */
    @ApiModelProperty(value = "收件人")
    private String recipient;

    /**
     * 抄送人
     */
    @ApiModelProperty(value = "抄送人")
    private String cc;

    /**
     * 主题
     */
    @ApiModelProperty(value = "主题")
    private String subject;

    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    private LocalDateTime time;

    /**
     * 已读标记
     */
    @ApiModelProperty(value = "已读标记")
    private boolean read;

}
