package com.bg.auth.security.authentication.email;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.bg.auth.exception.LoginException;
import com.bg.auth.service.IAuthService;
import com.bg.commons.enums.LoginTypeEnum;
import com.bg.commons.enums.StatusEnum;
import com.bg.commons.model.UserModel;
import com.bg.commons.utils.RedisUtil;
import com.bg.commons.utils.SpringUtil;
import com.bg.commons.utils.VerificationCode;
import com.bg.system.vo.SysUserVo;
import java.util.Collections;
import java.util.Objects;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @Author: jiewus
 * @Description: 邮箱登录provider类
 * @Date: 2023/9/25 10:00
 */
@Component
public class EmailAuthenticationProvider implements AuthenticationProvider {

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    if (!supports(authentication.getClass())) {
      return null;
    }
    IAuthService authService = SpringUtil.getBean(IAuthService.class);

    EmailAuthenticationToken authenticationToken = (EmailAuthenticationToken) authentication;
    String email = authenticationToken.getPrincipal().toString();
    String emailCode = authenticationToken.getVerifyCode().toString();
    String emailCodeCacheKey = authenticationToken.getVerifyCodeCacheKey().toString();

    SysUserVo user = authService.loadUserByEmail(email);
    if (Objects.isNull(user)) {
      throw new LoginException("account does not exist");
    }
    if (user.getStatus().equals(StatusEnum.DISABLE)) {
      throw new LoginException("account has been deactivated");
    }
    // 验证码校验
    RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
    String emailCodeCache = (String) redisUtil.get(VerificationCode.getEmailCode(emailCodeCacheKey));
    if (StringUtils.isEmpty(emailCodeCache)) {
      throw new LoginException("the verification code has expired, please reapply");
    }
    if (!emailCodeCache.equalsIgnoreCase(emailCode)) {
      throw new LoginException("verification code error!");
    }

    UserModel userModel = authService.createUserModel(user, email, LoginTypeEnum.EMAIL);

    authenticationToken = new EmailAuthenticationToken(userModel, Collections.emptyList());

    return authenticationToken;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return EmailAuthenticationToken.class.isAssignableFrom(authentication);
  }

}
