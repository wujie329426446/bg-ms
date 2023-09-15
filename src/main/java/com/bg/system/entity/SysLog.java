package com.bg.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bg.commons.enums.LogTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 系统日志
 *
 * @author jiewus
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("sys_log")
@Schema(description = "系统日志")
public class SysLog implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "主键ID")
  @TableId(value = "id", type = IdType.ASSIGN_UUID)
  private String id;

  @Schema(description = "日志链路ID")
  @TableField("trace_id")
  private String traceId;

  @Schema(description = "请求时间")
  @TableField("request_time")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime requestTime;

  @Schema(description = "响应时间")
  @TableField("response_time")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime responseTime;

  @Schema(description = "耗时，单位：毫秒")
  @TableField("diff_time")
  private Long diffTime;

  @Schema(description = "耗时描述")
  @TableField("diff_time_desc")
  private String diffTimeDesc;

  @Schema(description = "全路径")
  @TableField("request_url")
  private String requestUrl;

  @Schema(description = "权限编码")
  @TableField("permission_code")
  private String permissionCode;

  @Schema(description = "日志名称")
  @TableField("log_name")
  private String logName;

  @Schema(description = "请求方式，GET/POST")
  @TableField("request_method")
  private String requestMethod;

  @Schema(description = "内容类型")
  @TableField("content_type")
  private String contentType;

  @Schema(description = "是否是JSON请求映射参数")
  @TableField("is_request_body")
  private Boolean isRequestBody;

  @Schema(description = "token")
  @TableField("token")
  private String token;

  @Schema(description = "模块名称")
  @TableField("module_name")
  private String moduleName;

  @Schema(description = "controller类名称")
  @TableField("class_name")
  private String className;

  @Schema(description = "controller方法名称")
  @TableField("method_name")
  private String methodName;

  @Schema(description = "请求参数")
  @TableField("request_param")
  private String requestParam;

  @Schema(description = "用户ID")
  @TableField("user_id")
  private String userId;

  @Schema(description = "用户名")
  @TableField("user_name")
  private String userName;

  @Schema(description = "请求ip")
  @TableField("request_ip")
  private String requestIp;

  @Schema(description = "IP国家")
  @TableField("ip_country")
  private String ipCountry;

  @Schema(description = "IP省份")
  @TableField("ip_province")
  private String ipProvince;

  @Schema(description = "IP城市")
  @TableField("ip_city")
  private String ipCity;

  @Schema(description = "IP区域描述")
  @TableField("ip_area_desc")
  private String ipAreaDesc;

  @Schema(description = "IP运营商")
  @TableField("ip_isp")
  private String ipIsp;

  @Schema(description = "0:访问日志,1:新增,2:修改,3:删除,4:详情,5:所有列表,6:分页列表,7:其它查询,8:上传文件,9:登录,10:退出")
  @TableField("log_type")
  private LogTypeEnum logType;


  @Schema(description = "0:失败,1:成功")
  @TableField("response_success")
  private Boolean responseSuccess;

  @Schema(description = "响应结果状态码")
  @TableField("response_code")
  private Integer responseCode;

  @Schema(description = "响应结果消息")
  @TableField("response_message")
  private String responseMessage;

  @Schema(description = "响应数据")
  @TableField("response_data")
  private String responseData;

  @Schema(description = "异常类名称")
  @TableField("exception_name")
  private String exceptionName;

  @Schema(description = "异常信息")
  @TableField("exception_message")
  private String exceptionMessage;

  @Schema(description = "请求来源地址")
  @TableField("referer")
  private String referer;

  @Schema(description = "请求来源服务名")
  @TableField("origin")
  private String origin;

  @Schema(description = "请求来源类型")
  @TableField("source_type")
  private String sourceType;

  @Schema(description = "是否手机 0：否，1：是")
  @TableField("is_mobile")
  private Boolean isMobile;

  @Schema(description = "平台名称")
  @TableField("platform_name")
  private String platformName;

  @Schema(description = "浏览器名称")
  @TableField("browser_name")
  private String browserName;

  @Schema(description = "用户环境")
  @TableField("user_agent")
  private String userAgent;


}

