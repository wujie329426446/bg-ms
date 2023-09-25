package com.bg.auth.security.authentication.email;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.bg.auth.exception.LoginException;
import com.bg.auth.service.LoginService;
import com.bg.commons.enums.LoginTypeEnum;
import com.bg.commons.enums.StatusEnum;
import com.bg.commons.model.UserModel;
import com.bg.commons.utils.RedisUtil;
import com.bg.commons.utils.SpringUtil;
import com.bg.commons.utils.VerificationCodeUtil;
import com.bg.system.vo.SysUserVo;
import java.util.Collections;
import java.util.Objects;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @Author: issuser
 * @Description: 邮箱登录provider类
 * @Date: 2023/9/25 10:00
 */
@Component
public class EmailVerificationCodeAuthenticationProvider implements AuthenticationProvider {

  public EmailVerificationCodeAuthenticationProvider() {
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    if (!supports(authentication.getClass())) {
      return null;
    }
    LoginService loginService = SpringUtil.getBean(LoginService.class);

    EmailVerificationCodeAuthenticationToken authenticationToken = (EmailVerificationCodeAuthenticationToken) authentication;
    String email = authenticationToken.getPrincipal().toString();
    String emailCode = authenticationToken.getCredentials().toString();

    SysUserVo user = loginService.loadUserByEmail(email);
    if (Objects.isNull(user)) {
      throw new LoginException("account does not exist");
    }
    if (user.getStatus().equals(StatusEnum.DISABLE)) {
      throw new LoginException("account has been deactivated");
    }
    // 验证码校验
    this.checkEmailCode(email, emailCode);

    UserModel userModel = loginService.createUserModel(user, emailCode, email, LoginTypeEnum.EMAIL);

    authenticationToken = new EmailVerificationCodeAuthenticationToken(userModel, Collections.emptyList());

    return authenticationToken;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return EmailVerificationCodeAuthenticationToken.class.isAssignableFrom(authentication);
  }

  private void checkEmailCode(String email, String emailCode) {
    RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
    String verifyCode = (String) redisUtil.get(VerificationCodeUtil.getEmailCode(email));
    if (StringUtils.isEmpty(verifyCode)) {
      throw new LoginException("the verification code has expired, please reapply");
    }
    if (!verifyCode.equalsIgnoreCase(emailCode)) {
      throw new LoginException("verification code error!");
    }
  }

}
