package com.bg.auth.security.handle;

import com.bg.commons.api.ApiCode;
import com.bg.commons.api.ApiResult;
import com.bg.commons.utils.ServletUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * 认证入口
 *
 * @author jiewus
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

  /**
   * 认证失败
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    ApiResult apiResult = ApiResult.fail(ApiCode.UNAUTHORIZED, "认证失败，无法访问：" + request.getRequestURI());
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
    String json = objectWriter.writeValueAsString(apiResult);
    ServletUtil.renderString(response, json);
  }

}
