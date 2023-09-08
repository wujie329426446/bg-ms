package com.bg.system.service;

import com.bg.commons.enums.LoginTypeEnum;
import com.bg.commons.model.UserModel;
import com.bg.commons.service.BaseService;
import com.bg.system.entity.SysLogLogin;
import java.util.List;

/**
 * 登陆日志业务逻辑层接口
 *
 * @author jiewus
 */
public interface SysLogLoginService extends BaseService<SysLogLogin> {

  /**
   * 记录登陆信息
   *
   * @param userId    用户ID
   * @param userModel 用户信息
   * @param account   登陆账号
   * @param loginType 登陆类型
   * @param success   是否成功
   * @param message   返回消息
   */
  void recordLoginInfo(String userId, UserModel userModel, String account, LoginTypeEnum loginType, Integer success, String message);

}
