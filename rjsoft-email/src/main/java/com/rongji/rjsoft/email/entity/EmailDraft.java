package com.rongji.rjsoft.email.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 邮件草稿箱表
 * </p>
 *
 * @author JohnYehyo
 * @since 2022-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EmailDraft对象", description="邮件草稿箱表")
public class EmailDraft implements Serializable {

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

    @ApiModelProperty(value = "逻辑删除标记")
    private Integer delFlag;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
