package com.bg.commons.constant;

/**
 * 缓存前缀
 *
 * @author Tang
 */
public interface CachePrefix {

  /**
   * 缓存前缀
   */
  String SYSTEM = "bg:";

  /**
   * 登陆用户信息缓存前缀
   */
  String LOGIN_TOKENS = SYSTEM + "login_tokens:";

  /**
   * 用户邮箱登录验证码前缀
   */
  String EMAIL_CODE = "EMAIL_CODE:";

  /**
   * 用户手机登录验证码前缀
   */
  String PHONE_CODE = "PHONE_CODE:";

  /**
   * 字典数据缓存前缀
   */
  String DICT_TYPE = SYSTEM + "bg_type:";

}
