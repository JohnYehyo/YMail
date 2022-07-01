package com.rongji.rjsoft.email.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rongji.rjsoft.email.entity.EmailSendDetails;
import com.rongji.rjsoft.email.mapper.EmailSendDetailsMapper;
import com.rongji.rjsoft.email.service.IEmailSendDetailsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 已发送邮件明细表 服务实现类
 * </p>
 *
 * @author JohnYehyo
 * @since 2022-06-30
 */
@Service
public class EmailSendDetailsServiceImpl extends ServiceImpl<EmailSendDetailsMapper, EmailSendDetails> implements IEmailSendDetailsService {

}
