package com.rongji.rjsoft.email.service;

import com.rongji.rjsoft.email.ao.SendEmailAo;
import com.rongji.rjsoft.email.query.EmailListQuery;
import com.rongji.rjsoft.email.vo.EmailDetailsVo;
import com.rongji.rjsoft.email.vo.EmailPageVo;

/**
 * @description: 邮件服务
 * @author: JohnYehyo
 * @create: 2022-06-30 09:09:07
 */
public interface IEmailService {

    /**
     * 发送邮件
     *
     * @param sendEmailAo 参数体
     * @return 发送结果
     */
    boolean sendEmail(SendEmailAo sendEmailAo);

    /**
     * 邮件列表
     *
     * @param emailListQuery 查询对象
     * @return 邮件列表
     */
    EmailPageVo getEmailList(EmailListQuery emailListQuery);

    /**
     * 邮件详情
     *
     * @param box 文件夹
     * @param uid uid
     * @return 邮件详情
     */
    EmailDetailsVo getEmailByUid(String box, int uid);

    /**
     * 修改读取状态
     * @param uidArr uid数组
     * @param status 状态
     * @return 修改结果
     */
    boolean read(long[] uidArr, boolean status);

    /**
     * 删除收邮件
     *
     * @param uidArr 邮件id集合
     * @return 删除结果
     */
    boolean delete(long[] uidArr);
}
