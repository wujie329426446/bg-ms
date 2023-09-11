package com.bg.commons.exception;

public interface IBusinessException {

  /**
   * 获取错误码
   *
   * @return
   */
  Integer getCode();

  /**
   * 获取错误信息
   *
   * @return
   */
  String getMsg();
}
