package com.rongji.rjsoft.email.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rongji.rjsoft.email.entity.EmailSend;
import com.rongji.rjsoft.email.query.EmailSendQuery;
import com.rongji.rjsoft.email.vo.EmailSendVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 已发送邮件表 Mapper 接口
 * </p>
 *
 * @author JohnYehyo
 * @since 2022-06-30
 */
public interface EmailSendMapper extends BaseMapper<EmailSend> {

    IPage<EmailSendVo> getPage(@Param("param") EmailSendQuery emailSendQuery, IPage<EmailSendVo> page);
}
