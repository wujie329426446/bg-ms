package com.bg.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bg.commons.enums.LoginTypeEnum;
import com.bg.commons.model.LoginModel;
import com.bg.system.entity.SysLogLogin;
import com.bg.system.mapper.SysLogLoginMapper;
import com.bg.system.service.ISysLogLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 登陆日志业务逻辑层接口实现
 *
 * @author jiewus
 */
@Service
@Slf4j
@Transactional(rollbackFor = Throwable.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysLogLoginServiceImpl extends ServiceImpl<SysLogLoginMapper, SysLogLogin> implements ISysLogLoginService {

  /**
   * 记录登陆信息
   *
   * @param userId    用户ID
   * @param loginModel 用户信息
   * @param account   登陆账号
   * @param loginType 登陆类型
   * @param success   是否成功
   * @param message   返回消息
   */
  @Override
  public void recordLoginInfo(String userId, LoginModel loginModel, String account, LoginTypeEnum loginType, Integer success, String message) {
    SysLogLogin logLogin = new SysLogLogin();
    logLogin.setUserId(userId);
    logLogin.setAccount(account);
    logLogin.setLoginType(loginType);
    logLogin.setOs(loginModel.getOs());
    logLogin.setBrowser(loginModel.getBrowser());
    logLogin.setIp(loginModel.getIp());
    logLogin.setLocation(loginModel.getLocation());
    logLogin.setLoginTime(loginModel.getLoginTime());
    logLogin.setSuccess(success);
    logLogin.setMessage(message);
    this.save(logLogin);
  }

}
