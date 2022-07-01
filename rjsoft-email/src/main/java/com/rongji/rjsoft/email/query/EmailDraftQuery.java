package com.rongji.rjsoft.email.query;

import com.rongji.rjsoft.query.common.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @description: 草稿列表查询
 * @author: JohnYehyo
 * @create: 2022-06-30 15:08:06
 */
@ApiModel(value = "草稿列表查询")
public class EmailDraftQuery extends PageQuery implements Serializable {

    private static final long serialVersionUID = -1455776177897215482L;

    /**
     * 主题
     */
    @ApiModelProperty(value = "主题")
    private String subject;
}
