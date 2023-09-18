package com.bg.commons.api;

import com.alibaba.fastjson2.annotation.JSONField;
import com.bg.commons.constant.CommonConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * REST API 返回结果
 *
 * @author jiewus
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@Schema(description = "响应结果")
public class ApiResult<T> implements Serializable {

  private static final long serialVersionUID = 7594052194764993562L;

  @Schema(
      title = "code",
      description = "响应编码 200：成功，500：失败",
      format = "init32",
      requiredMode = RequiredMode.REQUIRED
  )
  private int code;

  @Schema(description = "响应结果 true：成功，false：失败")
  private boolean success;

  @Schema(
      title = "msg",
      description = "响应消息",
      accessMode = AccessMode.READ_ONLY,
      example = "成功或失败",
      requiredMode = RequiredMode.REQUIRED
  )
  private String message;

  @Schema(
      title = "data",
      description = "响应结果数据",
      accessMode = AccessMode.READ_ONLY
  )
  private T result;

  @Schema(description = "响应时间")
  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date time;

  @Schema(description = "日志链路ID")
  private String traceId;

  public static ApiResult success() {
    return success(null);
  }

  public static ApiResult success(Object data) {
    return result(ApiCode.SUCCESS, data);
  }

  public static ApiResult fail() {
    return fail(ApiCode.FAIL);
  }

  public static ApiResult fail(String message) {
    return fail(ApiCode.FAIL, message);
  }

  public static ApiResult fail(ApiCode apiCode) {
    return fail(apiCode, null);
  }

  public static ApiResult fail(ApiCode apiCode, String message) {
    return result(apiCode, message);
  }

  public static ApiResult fail(Integer code, String message) {
    ApiCode apiCode = ApiCode.getApiCode(code);
    if (Objects.isNull(apiCode)) {
      throw new RuntimeException("状态码不存在");
    }
    if (ApiCode.SUCCESS == apiCode) {
      throw new RuntimeException("失败结果状态码不能为" + ApiCode.SUCCESS.getCode());
    }
    return fail(apiCode, message);
  }

  public static ApiResult result(boolean flag) {
    if (flag) {
      return success();
    }
    return fail();
  }

  public static ApiResult result(ApiCode apiCode) {
    return result(apiCode, null);
  }

  public static ApiResult result(ApiCode apiCode, Object data) {
    return result(apiCode, null, data);
  }

  public static ApiResult<Map<String, Object>> okMap(String key, Object value) {
    Map<String, Object> map = new HashMap<>(1);
    map.put(key, value);
    return success(map);
  }

  public static ApiResult result(ApiCode apiCode, String message, Object data) {
    if (Objects.isNull(apiCode)) {
      throw new RuntimeException("结果状态码不能为空");
    }
    boolean success = false;
    if (ApiCode.SUCCESS == apiCode) {
      success = true;
    }
    String outMessage;
    if (StringUtils.isBlank(message)) {
      outMessage = apiCode.getMessage();
    } else {
      outMessage = message;
    }
    String traceId = MDC.get(CommonConstant.TRACE_ID);
    return ApiResult.builder()
        .code(apiCode.getCode())
        .message(outMessage)
        .result(data)
        .success(success)
        .time(new Date())
        .traceId(traceId)
        .build();
  }

  public static ApiResult result(Integer code, String message, Object data) {
    ApiCode apiCode = ApiCode.getApiCode(code);
    return result(apiCode, message, data);
  }

}
