package com.rongji.rjsoft.email.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rongji.rjsoft.common.util.CommonPageUtils;
import com.rongji.rjsoft.email.ao.EmailDraftAo;
import com.rongji.rjsoft.email.entity.EmailDraft;
import com.rongji.rjsoft.email.entity.EmailDraftDetails;
import com.rongji.rjsoft.email.mapper.EmailDraftDetailsMapper;
import com.rongji.rjsoft.email.mapper.EmailDraftMapper;
import com.rongji.rjsoft.email.query.EmailDraftQuery;
import com.rongji.rjsoft.email.service.IEmailDraftService;
import com.rongji.rjsoft.email.vo.EmailDraftDetailVo;
import com.rongji.rjsoft.email.vo.EmailDraftVo;
import com.rongji.rjsoft.enums.EnableEnum;
import com.rongji.rjsoft.vo.CommonPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 邮件草稿箱表 服务实现类
 * </p>
 *
 * @author JohnYehyo
 * @since 2022-06-30
 */
@Service
public class EmailDraftServiceImpl extends ServiceImpl<EmailDraftMapper, EmailDraft> implements IEmailDraftService {

    @Autowired
    private EmailDraftMapper emailDraftMapper;

    @Autowired
    private EmailDraftDetailsMapper emailDraftDetailsMapper;

    @Value("${mail.pop3.protocol}")
    private String POP3_PROTOCOL;

    @Value("${mail.imap.protocol}")
    private String IMAP_PROTOCOL;

    @Value("${mail.host}")
    private String HOST;

    @Value("${mail.smtp.port}")
    private int SMTP_PORT;

    @Value("${mail.pop3.port}")
    private int POP3_PORT;

    @Value("${mail.imap.port}")
    private int IMAP_PORT;

    @Value("${file.path}")
    private String path;

    @Value("${file.email}")
    private String emailPath;

    /**
     * 保存草稿
     *
     * @param emailDraftAo 参数体
     * @return 发送结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveEmailDraft(EmailDraftAo emailDraftAo) {
        emailDraftAo.setEmailHost(HOST);
        emailDraftAo.setEmailPort(String.valueOf(IMAP_PORT));
        EmailDraft emailDraft = new EmailDraft();
        BeanUtil.copyProperties(emailDraftAo, emailDraft);
        LocalDateTime now = LocalDateTime.now();
        emailDraft.setCreateTime(now);
        emailDraft.setUpdateTime(now);
        boolean result = emailDraftMapper.insert(emailDraft) > 0;
        EmailDraftDetails emailDraftDetails = new EmailDraftDetails();
        BeanUtil.copyProperties(emailDraftAo, emailDraftDetails);
        emailDraftDetails.setId(emailDraft.getId());
        boolean result1 = emailDraftDetailsMapper.insert(emailDraftDetails) > 0;
        return result && result1;
    }

    /**
     * 编辑草稿
     *
     * @param emailDraftAo 参数体
     * @return 发送结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editEmailDraft(EmailDraftAo emailDraftAo) {
        emailDraftAo.setEmailHost(HOST);
        emailDraftAo.setEmailPort(String.valueOf(IMAP_PORT));
        EmailDraft emailDraft = new EmailDraft();
        BeanUtil.copyProperties(emailDraftAo, emailDraft);
        emailDraft.setUpdateTime(LocalDateTime.now());
        boolean result = emailDraftMapper.updateById(emailDraft) > 0;
        EmailDraftDetails emailDraftDetails = new EmailDraftDetails();
        BeanUtil.copyProperties(emailDraftAo, emailDraftDetails);
        emailDraftDetails.setId(emailDraft.getId());
        boolean result1 = emailDraftDetailsMapper.updateById(emailDraftDetails) > 0;
        return result && result1;
    }

    /**
     * 删除草稿
     *
     * @param ids 草稿id集合
     * @return 删除结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteEmailDraft(String ids) {
        LambdaUpdateWrapper<EmailDraft> wrapper1 = new LambdaUpdateWrapper<>();
        EmailDraft emailDraft = new EmailDraft();
        emailDraft.setDelFlag(1);
        wrapper1.in(EmailDraft::getId, ids.split(","));
        boolean result = emailDraftMapper.update(emailDraft, wrapper1) > 0;

        LambdaUpdateWrapper<EmailDraftDetails> wrapper2 = new LambdaUpdateWrapper<>();
        EmailDraftDetails emailDraftDetails = new EmailDraftDetails();
        emailDraftDetails.setDelFlag(1);
        wrapper2.in(EmailDraftDetails::getId, ids.split(","));
        boolean result1 = emailDraftDetailsMapper.update(emailDraftDetails, wrapper2) > 0;
        return result && result1;
    }

    /**
     * 草稿箱
     * @param emailDraftQuery 查询参数体
     * @return 草稿箱
     */
    @Override
    public CommonPage<EmailDraftVo> getPage(EmailDraftQuery emailDraftQuery) {
        IPage<EmailDraftVo> page = new Page<>(emailDraftQuery.getCurrent(), emailDraftQuery.getPageSize());
        page = emailDraftMapper.getPage(emailDraftQuery, page);
        return CommonPageUtils.assemblyPage(page);
    }

    /**
     * 草稿详情
     * @param id 主键
     * @return 草稿详情
     */
    @Override
    public EmailDraftDetailVo details(Long id) {
        LambdaQueryWrapper<EmailDraft> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EmailDraft::getId, id).eq(EmailDraft::getDelFlag, EnableEnum.ENABLE.getCode());
        EmailDraft emailDraft = emailDraftMapper.selectOne(wrapper);
        if(null == emailDraft){
            return null;
        }
        EmailDraftDetails emailDraftDetails = emailDraftDetailsMapper.selectById(id);
        EmailDraftDetailVo emailDraftDetailVo = new EmailDraftDetailVo();
        BeanUtil.copyProperties(emailDraft, emailDraftDetailVo);
        BeanUtil.copyProperties(emailDraftDetails, emailDraftDetailVo);
        return emailDraftDetailVo;
    }
}
