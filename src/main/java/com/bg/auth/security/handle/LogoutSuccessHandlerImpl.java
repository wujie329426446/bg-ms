package com.bg.auth.security.handle;

import com.bg.auth.service.TokenService;
import com.bg.commons.api.ApiResult;
import com.bg.commons.model.UserModel;
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

  private final TokenService tokenService;

  public LogoutSuccessHandlerImpl(TokenService tokenService) {
    this.tokenService = tokenService;
  }

  /**
   * 登出成功
   */
  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    UserModel userModel = tokenService.get(request);
    if (userModel != null) {
      tokenService.delete(userModel.getToken());
    }

    ApiResult apiResult = ApiResult.success("登出成功");
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
    String json = objectWriter.writeValueAsString(apiResult);
    log.info("{} 登出成功.", userModel.getUsername());
    ServletUtil.renderString(response, json);
  }

}
