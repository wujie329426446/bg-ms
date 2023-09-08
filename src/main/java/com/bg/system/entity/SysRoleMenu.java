package com.bg.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.bg.commons.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import java.io.Serial;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 角色权限关系
 * </pre>
 *
 * @author jiewus
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "角色权限关系")
@TableName("sys_role_menu")
public class SysRoleMenu extends BaseEntity<SysRoleMenu> {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "角色id")
  @NotNull(message = "角色id不能为空")
  @TableField("role_id")
  private String roleId;

  @Schema(description = "权限id")
  @NotNull(message = "权限id不能为空")
  @TableField("permission_id")
  private String permissionId;

}
