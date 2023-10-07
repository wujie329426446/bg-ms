package com.bg.commons.api;

import java.util.Objects;

/**
 * <p>
 * REST API 响应码
 * </p>
 */
public enum ApiCode {

  SUCCESS(200, "操作成功"),

  BAD_REQUEST(400, "请求的数据格式不符"),

  UNAUTHORIZED(401, "非法访问"),

  NOT_PERMISSION(403, "没有权限"),

  NOT_FOUND(404, "你请求的资源不存在"),

  FAIL(500, "操作失败，服务器内部错误"),

  LOGIN_EXCEPTION(4000, "登录失败"),

  SYSTEM_EXCEPTION(5000, "系统异常"),

  PARAMETER_EXCEPTION(5001, "请求参数校验异常"),

  PARAMETER_PARSE_EXCEPTION(5002, "请求参数解析异常"),

  HTTP_MEDIA_TYPE_EXCEPTION(5003, "HTTP内容类型异常"),

  BG_EXCEPTION(5100, "系统处理异常"),

  BUSINESS_EXCEPTION(5101, "业务处理异常"),

  DAO_EXCEPTION(5102, "数据库处理异常"),

  VERIFICATION_CODE_EXCEPTION(5103, "验证码校验异常"),

  AUTHENTICATION_EXCEPTION(5104, "登录授权异常"),

  UNAUTHENTICATED_EXCEPTION(5105, "没有访问权限"),

  UNAUTHORIZED_EXCEPTION(5106, "没有访问权限"),

  JWTDECODE_EXCEPTION(5107, "Token解析异常"),

  HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION(5108, "METHOD NOT SUPPORTED"),

  TOKEN_EXCEPTION(5109, "Token为空或已过期，请重新登录");

  private final int code;
  private final String message;

  ApiCode(final int code, final String message) {
    this.code = code;
    this.message = message;
  }

  public static ApiCode getApiCode(Integer code) {
    if (Objects.isNull(code)) {
      return null;
    }
    ApiCode[] ecs = ApiCode.values();
    for (ApiCode ec : ecs) {
      if (ec.getCode() == code) {
        return ec;
      }
    }
    return null;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

}
