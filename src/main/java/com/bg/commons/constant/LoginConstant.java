package com.bg.commons.constant;

/**
 * @author jiewus
 * @date 2022/7/12
 **/
public interface LoginConstant {

  /**
   * bg系统认证header
   */
  String BG_HEADER = "Bg-Authorization";


  /**
   * 默认的token名称
   */
  String TOKEN_NAME = "token";

  /**
   * 默认的token过期时间 30分钟
   */
  Integer DEFAULT_TOKEN_EXPIRE_MINUTES = 30;

  /**
   * 管理员角色编码
   */
  String ADMIN_ROLE_CODE = "admin";

}
