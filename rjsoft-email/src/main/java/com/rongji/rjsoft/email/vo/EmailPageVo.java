package com.rongji.rjsoft.email.vo;

import com.rongji.rjsoft.vo.CommonPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 邮件分页
 * @author: JohnYehyo
 * @create: 2022-06-30 10:58:14
 */
@ApiModel(value = "邮件分页")
@Data
@AllArgsConstructor
public class EmailPageVo implements Serializable {

    private static final long serialVersionUID = -6339728599817355814L;

    /**
     * 分页结果
     */
    @ApiModelProperty(value = "分页结果")
    private CommonPage<EmailListVo> page;

    /**
     * 未读邮件数
     */
    @ApiModelProperty(value = "未读邮件数")
    private int unRead;
}
