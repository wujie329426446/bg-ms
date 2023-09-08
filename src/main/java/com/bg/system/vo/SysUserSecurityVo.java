package com.bg.system.vo;

import com.bg.commons.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 用户 Model
 *
 * @author jiewus
 */

@ToString(callSuper = true)
@Data
@Accessors(chain = true)
public class SysUserSecurityVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "用户id")
  private String userId;

  @Schema(description = "账号")
  private String username;

  @Schema(description = "用户显示名称")
  private String nickname;

  @Schema(description = "姓名")
  private String realname;

  @Schema(description = "用户密码")
  private String password;

  @Schema(description = "用户邮箱")
  private String email;

  @Schema(description = "最后登陆时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime loginDate;

  @Schema(description = "头像")
  private String avatar;

  @Schema(description = "角色编码集合")
  private List<String> roles;

  @Schema(description = "部门id")
  private String deptId;

  @Schema(description = "手机号码")
  private String phone;

  @Schema(description = "备注")
  private String remark;

  @Schema(description = "状态，0：禁用，1：启用，2：锁定")
  private Integer status;

  @Schema(description = "用户创建时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createTime;

  @Schema(description = "创建人姓名")
  private String creatorName;

  @Schema(description = "修改人姓名")
  private String updateName;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Schema(description = "修改时间")
  private LocalDateTime updateTime;

  @Schema(description = "部门")
  private SysDeptSecurityVo dept;

  @Schema(description = "删除标识")
  private Integer deleted;

  @Schema(description = "性别")
  private GenderEnum gender;

  @Schema(description = "loginIp")
  private String loginIp;
}
