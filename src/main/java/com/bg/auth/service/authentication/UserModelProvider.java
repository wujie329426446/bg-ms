package com.bg.auth.service.authentication;


import com.bg.commons.enums.LoginTypeEnum;
import com.bg.commons.model.UserModel;
import com.bg.system.vo.SysUserVo;

/**
 * 用户模型提供者
 *
 * @author jiewus
 */
public interface UserModelProvider {

  /**
   * 创建用户模型
   *
   * @param user      用户信息
   * @param password  明文密码
   * @param account   登陆账号
   * @param loginType 登陆类型
   * @return 用户模型
   */
  UserModel createUserModel(SysUserVo user, String password, String account, LoginTypeEnum loginType) throws Exception;

}
