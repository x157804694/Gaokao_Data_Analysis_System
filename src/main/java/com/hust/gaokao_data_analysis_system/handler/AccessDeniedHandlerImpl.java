package com.hust.gaokao_data_analysis_system.handler;

import com.alibaba.fastjson.JSON;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.common.ResponseStatusEnum;
import com.hust.gaokao_data_analysis_system.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 处理异常
        ResponseResult result = new ResponseResult(ResponseStatusEnum.USER_INSUFFICIENT_AUTHORITY);
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response,json);
    }
}
