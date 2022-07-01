package com.rongji.rjsoft.email.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 已发送邮件明细表
 * </p>
 *
 * @author JohnYehyo
 * @since 2022-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EmailSendDetails对象", description="已发送邮件明细表")
public class EmailSendDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "SMTP服务器域名")
    private String emailHost;

    @ApiModelProperty(value = "SMTP服务端口")
    private String emailPort;

    @ApiModelProperty(value = "是否认证 0 否 1 是")
    private Boolean emailAuth;

    @ApiModelProperty(value = "正文")
    private String emailContent;

    @ApiModelProperty(value = "附件地址")
    private String emailFiles;

    @ApiModelProperty(value = "逻辑删除 0 存在 1 删除")
    private Boolean delFlag;


}
