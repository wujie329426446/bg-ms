package com.bg.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bg.commons.core.validator.groups.Add;
import com.bg.commons.entity.BaseEntity;
import com.bg.commons.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 用户 Model
 *
 * @author jiewus
 */


@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户")
@TableName("sys_user")
public class SysUser extends BaseEntity<SysUser> {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "账号")
  @TableField("username")
  @NotBlank(message = "用户名不能为空", groups = {Add.class})
  private String username;

  @Schema(description = "部门id")
  @TableField("dept_id")
  private String deptId;

  @Schema(description = "真名")
  @TableField("realname")
  private String realname;

  @Schema(description = "用户显示名称")
  @TableField("nickname")
  private String nickname;

  @Schema(description = "用户邮箱")
  @TableField("email")
  @NotBlank(message = "用户邮箱不能为空", groups = {Add.class})
  private String email;

  @Schema(description = "手机号码")
  @NotBlank(message = "手机号码不能为空", groups = {Add.class})
  @TableField("phone")
  private String phone;

  @Schema(description = "性别{0=保密, 1=男, 2=女}")
  @TableField("gender")
  private GenderEnum gender;

  @Schema(description = "头像")
  @TableField("avatar")
  private String avatar;

  @Schema(description = "用户密码")
  @TableField("password")
  private String password;

  @Schema(description = "最后登录ip")
  @TableField("login_ip")
  private String loginIp;

  @Schema(description = "最后登陆时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @TableField("login_date")
  private LocalDateTime loginDate;

  @Schema(description = "角色集合")
  @TableField(exist = false)
  private List<String> roleIds;

}
