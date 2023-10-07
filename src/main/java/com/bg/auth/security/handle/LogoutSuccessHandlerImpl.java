package com.bg.auth.security.handle;

import com.bg.auth.security.filter.JwtAuthenticationTokenHandler;
import com.bg.commons.api.ApiResult;
import com.bg.commons.model.LoginModel;
import com.bg.commons.utils.ServletUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 登出入口
 *
 * @author jiewus
 */
@Component
@Slf4j
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

  private final JwtAuthenticationTokenHandler jwtAuthenticationTokenHandler;

  public LogoutSuccessHandlerImpl(JwtAuthenticationTokenHandler jwtAuthenticationTokenHandler) {
    this.jwtAuthenticationTokenHandler = jwtAuthenticationTokenHandler;
  }

  /**
   * 登出成功
   */
  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    LoginModel loginModel = jwtAuthenticationTokenHandler.get(request);
    if (loginModel != null) {
      jwtAuthenticationTokenHandler.delete(loginModel.getToken());
    }

    ApiResult apiResult = ApiResult.success("登出成功");
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
    String json = objectWriter.writeValueAsString(apiResult);
    log.info("{} 登出成功.", loginModel.getUsername());
    ServletUtil.renderString(response, json);
  }

}
