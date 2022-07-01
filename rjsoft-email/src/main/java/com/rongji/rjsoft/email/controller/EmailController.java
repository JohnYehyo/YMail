package com.rongji.rjsoft.email.controller;

import com.rongji.rjsoft.email.ao.SendEmailAo;
import com.rongji.rjsoft.email.query.EmailListQuery;
import com.rongji.rjsoft.email.service.IEmailService;
import com.rongji.rjsoft.email.vo.EmailDetailsVo;
import com.rongji.rjsoft.email.vo.EmailPageVo;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @description: 邮件服务
 * @author: JohnYehyo
 * @create: 2022-06-30 09:07:36
 */
@Api(tags = "邮件服务")
@RestController
@RequestMapping
public class EmailController {

    @Autowired
    private IEmailService emailService;


    /**
     * 发送邮件
     *
     * @param sendEmailAo 参数体
     * @return 发送结果
     */
    @ApiOperation(value = "发送邮件")
    @PostMapping
    public ResponseVo sendEmail(@Valid @RequestBody SendEmailAo sendEmailAo) {
        if (emailService.sendEmail(sendEmailAo)) {
            return ResponseVo.success("发送成功");
        }
        return ResponseVo.error("发送失败");
    }

    /**
     * 邮件详情
     *
     * @param box 文件夹
     * @param uid uid
     * @return 邮件详情
     */
    @ApiOperation(value = "邮件详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "box", value = "文件夹", required = true),
            @ApiImplicitParam(name = "uid", value = "邮件id", required = true)
    })
    @GetMapping(value = "/details/{box}/{uid}")
    public ResponseVo<EmailDetailsVo> email(@PathVariable String box, @PathVariable int uid) {
        return ResponseVo.response(ResponseEnum.SUCCESS, emailService.getEmailByUid(box, uid));
    }

    /**
     * 邮件列表
     *
     * @param emailListQuery 查询对象
     * @return 邮件列表
     */
    @ApiOperation(value = "邮件列表")
    @GetMapping(value = "list")
    public ResponseVo<EmailPageVo> sendEmail(@Valid EmailListQuery emailListQuery) {
        return ResponseVo.response(ResponseEnum.SUCCESS, emailService.getEmailList(emailListQuery));
    }

    /**
     * 修改读取状态
     *
     * @param uids 邮件id集合
     * @return 修改结果
     */
    @ApiOperation(value = "修改读取状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uids", value = "邮件id集合", required = true, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, dataType = "boolean")
    })
    @PostMapping(value = "/read/{uids}/{status}")
    public ResponseVo read(@PathVariable String uids, @PathVariable boolean status) {
        String[] uidArrays = uids.split(",");
        long[] uidArr = (long[])ConvertUtils.convert(uidArrays, long.class);
        if(emailService.read(uidArr, status)){
            return ResponseVo.success();
        }
        return ResponseVo.error();
    }

    /**
     * 删除收邮件
     *
     * @param uids 邮件id集合
     * @return 删除结果
     */
    @ApiOperation(value = "删除收邮件")
    @ApiImplicitParam(name = "uids", value = "邮件id集合", required = true, dataType = "String")
    @PostMapping(value = "/read/{uids}")
    public ResponseVo delete(@PathVariable String uids) {
        String[] uidArrays = uids.split(",");
        long[] uidArr = (long[])ConvertUtils.convert(uidArrays, long.class);
        if(emailService.delete(uidArr)){
            return ResponseVo.success();
        }
        return ResponseVo.error();
    }


}
