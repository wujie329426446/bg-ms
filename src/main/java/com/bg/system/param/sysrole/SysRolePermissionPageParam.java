package com.bg.system.param.sysrole;

import com.bg.commons.pagination.BasePageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 角色权限关系 查询参数对象
 * </pre>
 *
 * @author jiewus
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "角色权限关系查询参数")
public class SysRolePermissionPageParam extends BasePageParam {

  @Serial
  private static final long serialVersionUID = 1L;
}
