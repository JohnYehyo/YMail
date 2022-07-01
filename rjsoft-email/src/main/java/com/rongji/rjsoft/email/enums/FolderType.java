package com.rongji.rjsoft.email.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 文件夹分类
 * @author: JohnYehyo
 * @create: 2022-07-01 15:00:36
 */
@Getter
@AllArgsConstructor
public enum FolderType {


    /**
     * 收件箱
     */
    INBOX("INBOX", "收件箱"),
    /**
     * 草稿箱
     */
    DRAFTBOX("DRAFTBOX", "草稿箱"),
    /**
     * 已发送
     */
    SENDBOX("SENDBOX", "已发送"),
    /**
     * 已删除
     */
    DELETEDBOX("DELETEDBOX", "已删除");


    /**
     * 文件夹
     */
    private final String folder;

    /**
     * 描述
     */
    private final String description;
}
