package com.bg.auth.service;

import com.bg.auth.security.authentication.username.UsernameAuthenticationToken;
import com.bg.commons.api.ApiCode;
import com.bg.commons.constant.CommonConstant;
import com.bg.commons.enums.LoginTypeEnum;
import com.bg.commons.exception.BusinessException;
import com.bg.commons.model.LoginModel;
import com.bg.commons.model.UserModel;
import com.bg.system.service.ISysLogLoginService;
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

  private final ISysLogLoginService logLoginService;

  public LoginService(AuthenticationManager authenticationManager, TokenService tokenService, ISysLogLoginService logLoginService) {
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
    AbstractAuthenticationToken authenticationToken;

    LoginTypeEnum loginType = loginModel.getLoginType();
    String username = loginModel.getUsername();
    String password = loginModel.getPassword();

    Authentication authentication = switch (loginType) {
      case USER_NAME -> {
        authenticationToken = new UsernameAuthenticationToken(username, password);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        authentication = authenticationManager.authenticate(authenticationToken);
        yield authentication;
      }
      case EMAIL -> {
        // TODO 邮箱登录
        throw BusinessException.build(ApiCode.LOGIN_EXCEPTION.getCode(), "暂未开放邮箱登录方式");
      }
      case PHONE -> {
        // TODO 手机登录
        throw BusinessException.build(ApiCode.LOGIN_EXCEPTION.getCode(), "暂未开放手机登录方式");
      }
    };

    UserModel userModel = (UserModel) authentication.getPrincipal();
    String token = tokenService.createToken(userModel);

    logLoginService.recordLoginInfo(userModel.getUser().getUserId(), userModel, username, loginType, CommonConstant.SUCCESS_INT, "登陆成功");
    log.info("用户使用 {} 方式登录成功，登录账号：{}", loginType.getDesc(), username);

    return token;
  }

}
