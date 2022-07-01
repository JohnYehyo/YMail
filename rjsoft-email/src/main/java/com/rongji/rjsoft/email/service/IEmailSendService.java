package com.rongji.rjsoft.email.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rongji.rjsoft.email.ao.EmailDraftAo;
import com.rongji.rjsoft.email.ao.EmailSendAo;
import com.rongji.rjsoft.email.entity.EmailSend;
import com.rongji.rjsoft.email.query.EmailSendQuery;
import com.rongji.rjsoft.email.vo.EmailSendDetailVo;
import com.rongji.rjsoft.email.vo.EmailSendVo;
import com.rongji.rjsoft.vo.CommonPage;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 已发送邮件表 服务类
 * </p>
 *
 * @author JohnYehyo
 * @since 2022-06-30
 */
public interface IEmailSendService extends IService<EmailSend> {

    /**
     * 保存草稿
     *
     * @param emailSendAo 参数体
     * @return 发送结果
     */
    boolean saveEmailSend(EmailSendAo emailSendAo);

    /**
     * 发件箱
     * @param emailSendQuery 查询参数体
     * @return 发件箱
     */
    CommonPage<EmailSendVo> getPage(EmailSendQuery emailSendQuery);

    /**
     * 发送邮件详情
     * @param id 主键
     * @return 发送邮件详情
     */
    EmailSendDetailVo details(Long id);
}
