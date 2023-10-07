package com.bg.auth.service;

import com.bg.commons.enums.LoginTypeEnum;
import com.bg.commons.model.LoginModel;
import com.bg.commons.model.LoginParam;
import com.bg.system.entity.SysUser;
import java.io.IOException;
import java.util.Map;

/**
 * 权限认证service类
 *
 * @author issuser
 * @since 2023/09/25
 */
public interface IAuthService {

  /**
   * 账号登录-获取验证码
   *
   * @return
   * @throws IOException
   */
  Map<String, String> verify();

  /**
   * 邮箱登录-获取验证码
   *
   * @param email
   * @return
   */
  Map<String, String> emailVerify(String email);


  /**
   * 手机登录-获取验证码
   *
   * @param phone
   * @return
   */
  Map<String, String> phoneVerify(String phone);

  /**
   * 登录
   *
   * @param loginParam
   * @return
   */
  String login(LoginParam loginParam);

  SysUser loadUserByUsername(String username);

  SysUser loadUserByEmail(String email);

  SysUser loadUserByPhone(String phone);

  LoginModel createUserModel(SysUser user, String account, LoginTypeEnum loginType);

}
