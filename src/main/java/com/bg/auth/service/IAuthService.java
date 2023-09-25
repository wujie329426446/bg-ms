package com.bg.auth.service;

import com.bg.commons.enums.LoginTypeEnum;
import com.bg.commons.model.LoginModel;
import com.bg.commons.model.UserModel;
import com.bg.system.vo.SysUserVo;
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
  Map<String, String> verify() throws IOException;

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
   * @param loginModel
   * @return
   */
  String login(LoginModel loginModel);

  SysUserVo loadUserByUsername(String username);

  SysUserVo loadUserByEmail(String email);

  SysUserVo loadUserByPhone(String phone);

  UserModel createUserModel(SysUserVo user, String account, LoginTypeEnum loginType);

}
