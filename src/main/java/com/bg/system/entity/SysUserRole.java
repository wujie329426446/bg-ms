package com.bg.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bg.commons.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author jiewus
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "用户角色关联表")
@TableName("sys_user_role")
public class SysUserRole extends BaseEntity<SysUserRole> {

  @Serial
  private static final long serialVersionUID = 1L;

  @NotNull(message = "用户id不能为空")
  @Schema(description = "用户编号")
  @TableField("user_id")
  private String userId;

  @NotNull(message = "角色id不能为空")
  @Schema(description = "角色编号")
  @TableField("role_id")
  private String roleId;

}