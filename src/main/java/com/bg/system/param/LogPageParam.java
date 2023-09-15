package com.bg.system.param;

import com.bg.commons.enums.LogTypeEnum;
import com.bg.commons.pagination.BasePageParam;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统日志查询参数
 *
 * @author jiewus
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统日志查询参数")
public class LogPageParam extends BasePageParam {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "日志链路ID")
  private String traceId;

  @Schema(description = "模块名称")
  private String moduleName;

  @Schema(description = "请求路径")
  private String requestUrl;

  @Schema(description = "日志名称")
  private String logName;

  @Schema(description = "操作人")
  private String username;

  @Schema(description = "日志类型 0:访问日志,1:新增,2:修改,3:删除,4:详情,5:所有列表,6:分页列表,7:其它查询,8:上传文件,9:登录,10:退出")
  private LogTypeEnum logType;

  @Schema(description = "响应状态 false:失败,true:成功")
  private Boolean responseSuccess;

  @Schema(description = "请求IP")
  private String requestIp;

  @Schema(description = "创建开始时间")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createStartTime;

  @Schema(description = "创建结束时间")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createEndTime;

}

