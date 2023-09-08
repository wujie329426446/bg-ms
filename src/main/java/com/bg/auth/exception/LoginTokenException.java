package com.bg.auth.exception;

/**
 * 登录token异常
 *
 * @author jiewus
 */
public class LoginTokenException extends RuntimeException {

  public LoginTokenException(String message) {
    super(message);
  }

}
