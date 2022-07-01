package com.rongji.rjsoft.email.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 邮件草稿明细表
 * </p>
 *
 * @author JohnYehyo
 * @since 2022-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EmailDraftDetails对象", description="邮件草稿明细表")
public class EmailDraftDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.INPUT)
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

    @ApiModelProperty(value = "逻辑删除标记")
    private Integer delFlag;


}
