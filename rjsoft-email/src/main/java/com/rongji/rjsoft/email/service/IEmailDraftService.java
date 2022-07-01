package com.rongji.rjsoft.email.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rongji.rjsoft.email.ao.EmailDraftAo;
import com.rongji.rjsoft.email.entity.EmailDraft;
import com.rongji.rjsoft.email.query.EmailDraftQuery;
import com.rongji.rjsoft.email.vo.EmailDraftDetailVo;
import com.rongji.rjsoft.email.vo.EmailDraftVo;
import com.rongji.rjsoft.vo.CommonPage;

/**
 * <p>
 * 邮件草稿箱表 服务类
 * </p>
 *
 * @author JohnYehyo
 * @since 2022-06-30
 */
public interface IEmailDraftService extends IService<EmailDraft> {

    /**
     * 保存草稿
     * @param emailDraftAo 参数体
     * @return 发送结果
     */
    boolean saveEmailDraft(EmailDraftAo emailDraftAo);

    /**
     * 编辑草稿
     * @param emailDraftAo 参数体
     * @return 发送结果
     */
    boolean editEmailDraft(EmailDraftAo emailDraftAo);

    /**
     * 删除草稿
     * @param ids 草稿id集合
     * @return 删除结果
     */
    boolean deleteEmailDraft(String ids);

    /**
     * 草稿箱
     * @param emailDraftQuery 查询参数体
     * @return 草稿箱
     */
    CommonPage<EmailDraftVo> getPage(EmailDraftQuery emailDraftQuery);

    /**
     * 草稿详情
     * @param id 主键
     * @return 草稿详情
     */
    EmailDraftDetailVo details(Long id);
}
