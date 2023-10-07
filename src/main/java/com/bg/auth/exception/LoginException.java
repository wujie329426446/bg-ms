package com.bg.auth.exception;

import lombok.Data;

/**
 * 登录异常
 *
 * @author jiewus
 */
@Data
public class LoginException extends RuntimeException {

  /**
   * 错误码
   */
  private Integer code;

  /**
   * 错误描述
   */
  private String msg;

  public LoginException(String msg) {
    super(msg);
  }

  public LoginException(Integer code, String msg) {
    super(msg);
    this.code = code;
    this.msg = msg;
  }

}
