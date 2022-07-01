package com.rongji.rjsoft.common.util;

import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.exception.BusinessException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: Servlet工具
 * @author: JohnYehyo
 * @create: 2021-04-26 11:54:17
 */
public class ServletUtils {

    /**
     * 获取request
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        if (null == getRequestAttributes()){
            throw new BusinessException(ResponseEnum.BUSY);
        }
        if (null == getRequestAttributes().getRequest()){
            throw new BusinessException(ResponseEnum.BUSY);
        }
        return getRequestAttributes().getRequest();
    }

    /**
     * response结果
     *
     * @param response HttpServletResponse
     * @param string   结果
     * @param code   状态码
     */
    public static void response(HttpServletResponse response, String string, int code) {
        response.setStatus(code);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

}
