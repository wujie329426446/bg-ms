package com.bg.commons.model;

import com.bg.commons.entity.BaseEntity;
import com.bg.commons.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 * 用户模型
 *
 * @author Tang
 */
@Data
public class SysUserModel implements Serializable {

  @Serial
  private static final long serialVersionUID = 8680278499560297179L;

  @Schema(description = "用户ID")
  private String userId;

  @Schema(description = "部门ID")
  private String deptId;

  @Schema(description = "用户账号")
  private String username;

  @Schema(description = "昵称")
  private String nickname;

  @Schema(description = "邮箱")
  private String email;

  @Schema(description = "手机号码")
  private String phone;

  @Schema(description = "性别")
  private GenderEnum gender;

  @Schema(description = "头像地址")
  private String avatar;

  @Schema(description = "密码")
  private String password;

  @Schema(description = "帐号状态")
  private Integer status;

  @Schema(description = "删除标志")
  private String deleted;

  @Schema(description = "最后登录IP")
  private String loginIp;

  @Schema(description = "最后登录时间")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime loginDate;

  @Schema(description = "部门对象")
  private SysDeptModel dept;

  @Schema(description = "角色主键集合")
  private List<String> roleIds;

}
