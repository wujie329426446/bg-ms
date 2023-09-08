package com.bg.auth.service;

import com.bg.auth.security.authentication.username.UsernameAuthenticationToken;
import com.bg.commons.model.LoginModel;
import com.bg.commons.model.UserModel;
import com.bg.system.service.SysLogLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 登陆服务
 *
 * @author jiewus
 */
@Component
@Slf4j
public class LoginService {

  private final AuthenticationManager authenticationManager;

  private final TokenService tokenService;

  private final SysLogLoginService logLoginService;

  public LoginService(AuthenticationManager authenticationManager, TokenService tokenService, SysLogLoginService logLoginService) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
    this.logLoginService = logLoginService;
  }

  /**
   * 登陆
   *
   * @param loginModel 登陆用户信息
   * @return token
   */
  public String login(LoginModel loginModel) {
    Authentication authentication;
    AbstractAuthenticationToken authenticationToken;

    authenticationToken = new UsernameAuthenticationToken(loginModel.getUsername(), loginModel.getPassword());

    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    String account = authenticationToken.getPrincipal().toString();
    authentication = authenticationManager.authenticate(authenticationToken);

    UserModel userModel = (UserModel) authentication.getPrincipal();

    String token = tokenService.createToken(userModel);

    logLoginService.recordLoginInfo(userModel.getUser().getUserId(), userModel, account, loginModel.getLoginType(), 1, "登陆成功");
    log.info("用户使用 {} 方式登陆成功，登陆账号：{}", loginModel.getLoginType(), account);

    return token;
  }

}
