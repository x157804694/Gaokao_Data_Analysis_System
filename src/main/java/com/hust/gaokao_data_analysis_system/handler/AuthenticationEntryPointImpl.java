package com.hust.gaokao_data_analysis_system.handler;

import com.alibaba.fastjson.JSON;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.common.ResponseStatusEnum;
import com.hust.gaokao_data_analysis_system.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 处理异常
        ResponseResult result = new ResponseResult(ResponseStatusEnum.UNAUTHORIZED);
        if (authException instanceof InternalAuthenticationServiceException){
            String message = authException.getMessage();
            result.setMessage(message);
        }
        else if (authException instanceof BadCredentialsException){
            result.setMessage("密码错误，请重新检查");
        }
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response,json);
    }
}
