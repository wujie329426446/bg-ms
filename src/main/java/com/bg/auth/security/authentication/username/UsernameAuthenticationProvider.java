package com.bg.auth.security.authentication.username;

import com.bg.auth.exception.LoginException;
import com.bg.auth.service.LoginService;
import com.bg.commons.enums.LoginTypeEnum;
import com.bg.commons.enums.StatusEnum;
import com.bg.commons.model.UserModel;
import com.bg.commons.utils.SecurityUtil;
import com.bg.commons.utils.SpringUtil;
import com.bg.system.vo.SysUserVo;
import java.util.Collections;
import java.util.Objects;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 用户名密码身份验证
 *
 * @author jiewus
 */
@Component
public class UsernameAuthenticationProvider implements AuthenticationProvider {

  public UsernameAuthenticationProvider() {
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    if (!supports(authentication.getClass())) {
      return null;
    }
    LoginService loginService = SpringUtil.getBean(LoginService.class);

    UsernameAuthenticationToken authenticationToken = (UsernameAuthenticationToken) authentication;
    String username = authenticationToken.getPrincipal().toString();
    String password = authenticationToken.getCredentials().toString();

    SysUserVo user = loginService.loadUserByUsername(username);
    if (Objects.isNull(user)) {
      throw new LoginException("account does not exist");
    }
    if (!SecurityUtil.matchesPassword(password, user.getPassword())) {
      throw new LoginException("password error");
    }
    if (user.getStatus().equals(StatusEnum.DISABLE)) {
      throw new LoginException("account has been deactivated");
    }

    UserModel userModel = loginService.createUserModel(user, password, username, LoginTypeEnum.USER_NAME);

    authenticationToken = new UsernameAuthenticationToken(userModel, Collections.emptyList());

    return authenticationToken;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernameAuthenticationToken.class.isAssignableFrom(authentication);
  }

}
