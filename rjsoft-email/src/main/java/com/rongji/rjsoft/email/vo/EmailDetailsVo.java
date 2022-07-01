package com.rongji.rjsoft.email.vo;

import com.rongji.rjsoft.email.entity.DocInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 邮件详情
 * @author: JohnYehyo
 * @create: 2022-06-30 09:52:26
 */
@ApiModel(value = "邮件详情")
@Data
public class EmailDetailsVo implements Serializable {

    private static final long serialVersionUID = 6628737988749914812L;

    /**
     * 邮件正文
     */
    @ApiModelProperty(value = "邮件正文")
    private String content;

    /**
     * 附件
     */
    @ApiModelProperty(value = "附件")
    private List<DocInfo> files;
}
