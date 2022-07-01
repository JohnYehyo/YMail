package com.rongji.rjsoft.email.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 已发送邮件表
 * </p>
 *
 * @author JohnYehyo
 * @since 2022-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EmailSend对象", description="已发送邮件表")
public class EmailSend implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "发件人邮箱")
    private String emailFrom;

    @ApiModelProperty(value = "发件人")
    private String emailUser;

    @ApiModelProperty(value = "收件人邮箱")
    private String emailTo;

    @ApiModelProperty(value = "抄送人邮箱")
    private String emailCc;

    @ApiModelProperty(value = "密送人邮箱")
    private String emailBcc;

    @ApiModelProperty(value = "主题")
    private String emailSubject;

    @ApiModelProperty(value = "邮件类型")
    private Integer emailType;

    @ApiModelProperty(value = "逻辑删除 0 存在 1 删除")
    private Boolean delFlag;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
