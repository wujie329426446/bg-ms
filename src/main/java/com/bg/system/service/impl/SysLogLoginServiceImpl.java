package com.bg.system.service.impl;

import com.bg.commons.enums.LoginTypeEnum;
import com.bg.commons.model.UserModel;
import com.bg.commons.service.impl.BaseServiceImpl;
import com.bg.commons.utils.SecurityUtil;
import com.bg.system.entity.SysLogLogin;
import com.bg.system.mapper.SysLogLoginMapper;
import com.bg.system.service.SysLogLoginService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 登陆日志业务逻辑层接口实现
 *
 * @author jiewus
 */
@Service
public class SysLogLoginServiceImpl extends BaseServiceImpl<SysLogLoginMapper, SysLogLogin> implements SysLogLoginService {

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
  @Override
  public void recordLoginInfo(String userId, UserModel userModel, String account, LoginTypeEnum loginType, Integer success, String message) {
    SysLogLogin logLogin = new SysLogLogin();
    logLogin.setUserId(userId);
    logLogin.setAccount(account);
    logLogin.setLoginType(loginType);
    logLogin.setOs(userModel.getOs());
    logLogin.setBrowser(userModel.getBrowser());
    logLogin.setIp(userModel.getIp());
    logLogin.setLocation(userModel.getLocation());
    logLogin.setLoginTime(userModel.getLoginTime());
    logLogin.setSuccess(success);
    logLogin.setMessage(message);
    this.save(logLogin);
  }

}
