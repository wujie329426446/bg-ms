package com.bg.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bg.commons.enums.LoginTypeEnum;
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
import lombok.experimental.Accessors;

/**
 * 登陆日志实体类 sys_log_login
 *
 * @author jiewus
 */
@Data
@Accessors(chain = true)
@TableName("sys_log_login")
@Schema(description = "登陆日志实体类")
public class SysLogLogin implements Serializable {

  @Serial
  private static final long serialVersionUID = -7179007173307071853L;

  @Schema(description = "日志ID")
  @TableId(value = "id", type = IdType.ASSIGN_UUID)
  private String id;

  @Schema(description = "用户ID")
  @TableField("user_id")
  private String userId;

  @Schema(description = "登陆账号")
  @TableField("account")
  private String account;

  @Schema(description = "登陆类型")
  @TableField("login_type")
  private LoginTypeEnum loginType;

  @Schema(description = "操作系统")
  @TableField("os")
  private String os;

  @Schema(description = "浏览器类型")
  @TableField("browser")
  private String browser;

  @Schema(description = "登录IP地址")
  @TableField("ip")
  private String ip;

  @Schema(description = "登录地点")
  @TableField("location")
  private String location;

  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Schema(description = "登录时间")
  @TableField("login_time")
  private LocalDateTime loginTime;

  @Schema(description = "是否成功：0：失败，1：成功")
  @TableField("success")
  private Integer success;

  @Schema(description = "返回消息")
  @TableField("message")
  private String message;

}
