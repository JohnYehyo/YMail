package com.rongji.rjsoft.email.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rongji.rjsoft.common.util.CommonPageUtils;
import com.rongji.rjsoft.email.ao.EmailDraftAo;
import com.rongji.rjsoft.email.ao.EmailSendAo;
import com.rongji.rjsoft.email.entity.EmailDraft;
import com.rongji.rjsoft.email.entity.EmailDraftDetails;
import com.rongji.rjsoft.email.entity.EmailSend;
import com.rongji.rjsoft.email.entity.EmailSendDetails;
import com.rongji.rjsoft.email.mapper.EmailSendDetailsMapper;
import com.rongji.rjsoft.email.mapper.EmailSendMapper;
import com.rongji.rjsoft.email.query.EmailSendQuery;
import com.rongji.rjsoft.email.service.IEmailSendService;
import com.rongji.rjsoft.email.vo.EmailDraftDetailVo;
import com.rongji.rjsoft.email.vo.EmailDraftVo;
import com.rongji.rjsoft.email.vo.EmailSendDetailVo;
import com.rongji.rjsoft.email.vo.EmailSendVo;
import com.rongji.rjsoft.enums.EnableEnum;
import com.rongji.rjsoft.vo.CommonPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 已发送邮件表 服务实现类
 * </p>
 *
 * @author JohnYehyo
 * @since 2022-06-30
 */
@Service
public class EmailSendServiceImpl extends ServiceImpl<EmailSendMapper, EmailSend> implements IEmailSendService {


    @Autowired
    private EmailSendMapper emailSendMapper;

    @Autowired
    private EmailSendDetailsMapper emailSendDetailsMapper;

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
     * @param emailSendAo 参数体
     * @return 发送结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveEmailSend(EmailSendAo emailSendAo) {
        emailSendAo.setEmailHost(HOST);
        emailSendAo.setEmailPort(String.valueOf(IMAP_PORT));
        EmailSend emailSend = new EmailSend();
        BeanUtil.copyProperties(emailSendAo, emailSend);
        LocalDateTime now = LocalDateTime.now();
        emailSend.setCreateTime(now);
        boolean result = emailSendMapper.insert(emailSend) > 0;
        EmailSendDetails emailSendDetails = new EmailSendDetails();
        BeanUtil.copyProperties(emailSendAo, emailSendDetails);
        emailSendDetails.setId(emailSend.getId());
        boolean result1 = emailSendDetailsMapper.insert(emailSendDetails) > 0;
        return result && result1;
    }

    /**
     * 发件箱
     * @param emailSendQuery 查询参数体
     * @return 发件箱
     */
    @Override
    public CommonPage<EmailSendVo> getPage(EmailSendQuery emailSendQuery) {
        IPage<EmailSendVo> page = new Page<>(emailSendQuery.getCurrent(), emailSendQuery.getPageSize());
        page = emailSendMapper.getPage(emailSendQuery, page);
        return CommonPageUtils.assemblyPage(page);
    }

    /**
     * 发送邮件详情
     * @param id 主键
     * @return 发送邮件详情
     */
    @Override
    public EmailSendDetailVo details(Long id) {
        LambdaQueryWrapper<EmailSend> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EmailSend::getId, id).eq(EmailSend::getDelFlag, EnableEnum.ENABLE.getCode());
        EmailSend emailSend = emailSendMapper.selectOne(wrapper);
        if(null == emailSend){
            return null;
        }
        EmailSendDetails emailSendDetails = emailSendDetailsMapper.selectById(id);
        EmailSendDetailVo emailSendDetailVo = new EmailSendDetailVo();
        BeanUtil.copyProperties(emailSend, emailSendDetailVo);
        BeanUtil.copyProperties(emailSendDetails, emailSendDetailVo);
        return emailSendDetailVo;
    }
}
