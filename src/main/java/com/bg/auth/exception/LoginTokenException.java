package com.bg.auth.exception;

import lombok.Data;

/**
 * 登录token异常
 *
 * @author jiewus
 */
@Data
public class LoginTokenException extends RuntimeException {

  public LoginTokenException(String message) {
    super(message);
  }

}
