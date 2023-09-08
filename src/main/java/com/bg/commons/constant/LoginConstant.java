package com.bg.commons.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author jiewus
 * @date 2022/7/12
 **/
public class LoginConstant {

  /**
   * bg系统认证header
   */
  public static final String BG_HEADER = "Bg-Authorization";


  /**
   * 默认的token名称
   */
  public static final String TOKEN_NAME = "token";

  /**
   * 默认的token过期时间 30分钟
   */
  public static final Integer DEFAULT_TOKEN_EXPIRE_MINUTES = 30;

  /**
   * 管理员角色编码
   */
  public static final String ADMIN_ROLE_CODE = "admin";

  /**
   * 管理员admin角色ID
   */
  public static List<String> ADMIN_ROLE_ID_LIST = Arrays.asList();

}
