package com.bg.commons.exception;

/**
 * 业务异常
 *
 * @author jiewus
 * @date 2018-11-08
 */
public class BusinessException extends RuntimeException {

  public BusinessException(String message) {
    super(message);
  }
}
