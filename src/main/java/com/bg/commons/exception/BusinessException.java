package com.bg.commons.exception;

import com.bg.commons.api.ApiCode;
import java.util.List;
import lombok.Data;

/**
 * @ClassName BusinessException
 * @Description acfa异常类
 * @Author Chen Zheng
 * @Date 2020/6/19 7:00 PM
 */
@Data
public class BusinessException extends RuntimeException {

  /**
   * 错误码
   */
  private Integer code;

  /**
   * 错误描述
   */
  private String msg;

  /**
   * 错误数据
   */
  private List<String> errorList;

  private Object errorData;

  private BusinessException() {
    super();
  }


  private BusinessException(IBusinessException ee) {
    super(ee.getMsg());
    this.code = ee.getCode();
    this.msg = ee.getMsg();
  }

  private BusinessException(IBusinessException ee, Throwable cause) {
    super(ee.getMsg(), cause);
    this.code = ee.getCode();
    this.msg = ee.getMsg();
  }

  private BusinessException(String msg) {
    super(msg);
    this.code = ApiCode.BAD_REQUEST.getCode();
    this.msg = msg;
  }

  private BusinessException(String msg, List<String> data) {
    super(msg);
    this.code = ApiCode.BAD_REQUEST.getCode();
    this.msg = msg;
    this.errorList = data;
  }

  private BusinessException(String msg, Object errorData) {
    super(msg);
    this.code = ApiCode.BAD_REQUEST.getCode();
    this.msg = msg;
    this.errorData = errorData;
  }

  private BusinessException(Integer code, String msg) {
    super(msg);
    this.code = code;
    this.msg = msg;
  }

  private BusinessException(Integer code, String msg, Throwable cause) {
    super(msg, cause);
    this.code = code;
    this.msg = msg;
  }

  public static BusinessException build(String msg) {
    return new BusinessException(msg);
  }

  public static BusinessException build(String msg, List<String> errorData) {
    return new BusinessException(msg, errorData);
  }

  public static BusinessException build(String msg, Object errorData) {
    return new BusinessException(msg, errorData);
  }

  public static BusinessException build(Integer code, String msg) {
    return new BusinessException(code, msg);
  }

  public static BusinessException build(IBusinessException ee) {
    return new BusinessException(ee);
  }

}
