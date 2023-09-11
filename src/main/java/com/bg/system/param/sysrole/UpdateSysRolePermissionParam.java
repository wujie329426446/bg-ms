package com.bg.system.param.sysrole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author jiewus
 **/
@Data
@Schema(description = "修改系统角色权限参数")
public class UpdateSysRolePermissionParam implements Serializable {

  @Serial
  private static final long serialVersionUID = -672108684986772098L;

  @Schema(description = "角色ID")
  @NotNull(message = "角色ID不能为空")
  private String roleId;

  @Schema(description = "权限ID集合")
  private List<String> permissionIds;

}
