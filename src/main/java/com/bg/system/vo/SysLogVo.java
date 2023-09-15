package com.bg.system.vo;

import com.bg.commons.enums.LogTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 系统日志VO
 *
 * @author jiewus
 */
@Data
@Schema(description = "系统日志查询结果")
public class SysLogVo implements Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "主键")
  private String id;

  @Schema(description = "日志链路ID")
  private String traceId;

  @Schema(description = "请求时间")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime requestTime;

  @Schema(description = "响应时间")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime responseTime;

  @Schema(description = "耗时，单位：毫秒")
  private Long diffTime;

  @Schema(description = "耗时描述")
  private String diffTimeDesc;

  @Schema(description = "全路径")
  private String requestUrl;

  @Schema(description = "日志名称")
  private String logName;

  @Schema(description = "请求方式，GET/POST")
  private String requestMethod;

  @Schema(description = "模块名称")
  private String moduleName;

  @Schema(description = "请求参数")
  private String requestParam;

  @Schema(description = "用户名")
  private String username;

  @Schema(description = "请求ip")
  private String requestIp;

  @Schema(description = "IP区域描述")
  private String ipAreaDesc;

  @Schema(description = "0:访问日志,1:新增,2:修改,3:删除,4:详情,5:所有列表,6:分页列表,7:其它查询,8:上传文件,9:登录,10:退出")
  private LogTypeEnum logType;

  @Schema(description = "0:失败,1:成功")
  private Boolean responseSuccess;

  @Schema(description = "响应结果状态码")
  private Integer responseCode;

  @Schema(description = "响应结果消息")
  private String responseMessage;

  @Schema(description = "异常类名称")
  private String exceptionName;

  @Schema(description = "异常信息")
  private String exceptionMessage;

  @Schema(description = "是否手机 0：否，1：是")
  private Boolean isMobile;

  @Schema(description = "平台名称")
  private String platformName;

  @Schema(description = "浏览器名称")
  private String browserName;

}

