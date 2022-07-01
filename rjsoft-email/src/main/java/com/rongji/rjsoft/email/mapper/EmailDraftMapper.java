package com.rongji.rjsoft.email.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rongji.rjsoft.email.entity.EmailDraft;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rongji.rjsoft.email.query.EmailDraftQuery;
import com.rongji.rjsoft.email.vo.EmailDraftVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 邮件草稿箱表 Mapper 接口
 * </p>
 *
 * @author JohnYehyo
 * @since 2022-06-30
 */
public interface EmailDraftMapper extends BaseMapper<EmailDraft> {

    IPage<EmailDraftVo> getPage(@Param("params") EmailDraftQuery emailDraftQuery, IPage<EmailDraftVo> page);
}
