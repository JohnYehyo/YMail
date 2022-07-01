package com.rongji.rjsoft.email.controller;


import com.rongji.rjsoft.email.ao.EmailDraftAo;
import com.rongji.rjsoft.email.query.EmailDraftQuery;
import com.rongji.rjsoft.email.query.EmailSendQuery;
import com.rongji.rjsoft.email.service.IEmailSendService;
import com.rongji.rjsoft.email.vo.EmailDraftDetailVo;
import com.rongji.rjsoft.email.vo.EmailDraftVo;
import com.rongji.rjsoft.email.vo.EmailSendDetailVo;
import com.rongji.rjsoft.email.vo.EmailSendVo;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.exception.BusinessException;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 已发送邮件表 前端控制器
 * </p>
 *
 * @author JohnYehyo
 * @since 2022-06-30
 */
@Api(tags = "发件箱管理")
@RestController
@RequestMapping("send")
public class EmailSendController {

    @Autowired
    private IEmailSendService emailSendService;

    /**
     * 发件箱
     * @param emailSendQuery 查询参数体
     * @return 发件箱
     */
    @ApiOperation(value = "发件箱")
    @GetMapping(value = "page")
    public ResponseVo<CommonPage<EmailSendVo>> page(EmailSendQuery emailSendQuery){
        return ResponseVo.response(ResponseEnum.SUCCESS, emailSendService.getPage(emailSendQuery));
    }

    /**
     * 发送邮件详情
     * @param id 主键
     * @return 发送邮件详情
     */
    @ApiOperation(value = "发送邮件详情")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping(value = "{id}")
    public ResponseVo<EmailSendDetailVo> details(@PathVariable Long id){
        return ResponseVo.response(ResponseEnum.SUCCESS, emailSendService.details(id));
    }

}
