package com.rongji.rjsoft.email.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description: 邮件草稿
 * @author: JohnYehyo
 * @create: 2022-06-30 14:28:53
 */
@ApiModel(value = "邮件草稿视图")
@Data
public class EmailDraftVo {

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

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
}
