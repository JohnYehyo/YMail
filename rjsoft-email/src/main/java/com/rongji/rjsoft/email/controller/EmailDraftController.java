package com.rongji.rjsoft.email.controller;

import com.rongji.rjsoft.email.ao.EmailDraftAo;
import com.rongji.rjsoft.email.query.EmailDraftQuery;
import com.rongji.rjsoft.email.service.IEmailDraftService;
import com.rongji.rjsoft.email.vo.EmailDraftDetailVo;
import com.rongji.rjsoft.email.vo.EmailDraftVo;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.exception.BusinessException;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 邮件草稿
 * @author: JohnYehyo
 * @create: 2022-06-30 14:39:49
 */
@Api(tags = "草稿箱管理")
@RestController
@RequestMapping(value = "draft")
public class EmailDraftController {

    @Autowired
    private IEmailDraftService draftService;


    /**
     * 保存草稿
     * @param emailDraftAo 参数体
     * @return 发送结果
     */
    @ApiOperation(value = "保存草稿")
    @PostMapping(value = "draft")
    public ResponseVo saveEmailDraft(@Validated(EmailDraftAo.save.class) @RequestBody EmailDraftAo emailDraftAo) {
        if (draftService.saveEmailDraft(emailDraftAo)) {
            return ResponseVo.success("保存成功");
        }
        return ResponseVo.error("保存失败");
    }

    /**
     * 编辑草稿
     * @param emailDraftAo 参数体
     * @return 发送结果
     */
    @ApiOperation(value = "编辑草稿")
    @PutMapping(value = "draft")
    public ResponseVo editEmailDraft(@Validated(EmailDraftAo.edit.class) @RequestBody EmailDraftAo emailDraftAo) {
        if (draftService.editEmailDraft(emailDraftAo)) {
            return ResponseVo.success("编辑成功");
        }
        return ResponseVo.error("编辑失败");
    }

    /**
     * 删除草稿
     * @param ids 草稿id集合
     * @return 删除结果
     */
    @ApiOperation(value = "删除草稿")
    @ApiImplicitParam(name = "ids", value = "草稿id集合", required = true)
    @DeleteMapping(value = "draft/{ids}")
    public ResponseVo deleteEmailDraft(@PathVariable String ids) {
        if(StringUtils.isEmpty(ids)){
            throw new BusinessException(ResponseEnum.FAIL.getCode(), "请至少选择一条记录!");
        }
        if (draftService.deleteEmailDraft(ids)) {
            return ResponseVo.success("删除成功");
        }
        return ResponseVo.error("删除失败");
    }

    /**
     * 草稿箱
     * @param emailDraftQuery 查询参数体
     * @return 草稿箱
     */
    @ApiOperation(value = "草稿箱")
    @GetMapping(value = "page")
    public ResponseVo<CommonPage<EmailDraftVo>> page(EmailDraftQuery emailDraftQuery){
        return ResponseVo.response(ResponseEnum.SUCCESS, draftService.getPage(emailDraftQuery));
    }

    /**
     * 草稿详情
     * @param id 主键
     * @return 草稿详情
     */
    @ApiOperation(value = "草稿详情")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping(value = "{id}")
    public ResponseVo<EmailDraftDetailVo> details(@PathVariable Long id){
        return ResponseVo.response(ResponseEnum.SUCCESS, draftService.details(id));
    }
}
