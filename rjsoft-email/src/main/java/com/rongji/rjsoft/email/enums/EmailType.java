package com.rongji.rjsoft.email.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 邮件类型
 * @author: JohnYehyo
 * @create: 2022-06-30 09:39:49
 */
@Getter
@AllArgsConstructor
public enum EmailType {

    /**
     * 普通文本
     */
    TEXT(0, "普通文本"),

    /**
     * HTML
     */
    HTML(1, "html");

    /**
     * 值
     */
    private final int code;

    /**
     * 描述
     */
    private final String description;
}
