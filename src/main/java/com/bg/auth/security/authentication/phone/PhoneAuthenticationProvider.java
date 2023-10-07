package com.bg.auth.security.authentication.phone;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.bg.auth.exception.LoginException;
import com.bg.auth.security.authentication.email.EmailAuthenticationToken;
import com.bg.auth.service.IAuthService;
import com.bg.commons.api.ApiCode;
import com.bg.commons.enums.LoginTypeEnum;
import com.bg.commons.enums.StatusEnum;
import com.bg.commons.model.LoginModel;
import com.bg.commons.utils.RedisUtil;
import com.bg.commons.utils.SpringUtil;
import com.bg.commons.utils.VerifyCodeUtil;
import com.bg.system.entity.SysUser;
import com.bg.system.vo.SysUserVo;
import java.util.Collections;
import java.util.Objects;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @Author: jiewus
 * @Description: 手机登录provider类
 * @Date: 2023/9/25 10:00
 */
@Component
public class PhoneAuthenticationProvider implements AuthenticationProvider {

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    if (!supports(authentication.getClass())) {
      return null;
    }
    IAuthService authService = SpringUtil.getBean(IAuthService.class);

    PhoneAuthenticationToken authenticationToken = (PhoneAuthenticationToken) authentication;
    String phone = authenticationToken.getPrincipal().toString();
    String phoneCode = authenticationToken.getVerifyCode().toString();
    String phoneCodeCacheKey = authenticationToken.getVerifyCodeCacheKey().toString();

    SysUser user = authService.loadUserByPhone(phone);
    if (Objects.isNull(user)) {
      throw new LoginException(ApiCode.LOGIN_EXCEPTION.getCode(), "phone does not exist");
    }
    if (user.getStatus().equals(StatusEnum.DISABLE)) {
      throw new LoginException(ApiCode.LOGIN_EXCEPTION.getCode(), "phone has been deactivated");
    }
    // 验证码校验
    RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
    String phoneCodeCache = (String) redisUtil.get(VerifyCodeUtil.getPhoneCode(phoneCodeCacheKey));
    if (StringUtils.isEmpty(phoneCodeCache)) {
      throw new LoginException(ApiCode.VERIFICATION_CODE_EXCEPTION.getCode(), "the verification code has expired, please reapply");
    }
    if (!phoneCodeCache.equalsIgnoreCase(phoneCode)) {
      throw new LoginException(ApiCode.VERIFICATION_CODE_EXCEPTION.getCode(), "verification code error!");
    }

    LoginModel loginModel = authService.createUserModel(user, phone, LoginTypeEnum.PHONE);

    authenticationToken = new PhoneAuthenticationToken(loginModel, Collections.emptyList());

    return authenticationToken;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return EmailAuthenticationToken.class.isAssignableFrom(authentication);
  }

}
