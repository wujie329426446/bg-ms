package com.bg.system.param;

import com.bg.commons.enums.StatusEnum;
import com.bg.commons.pagination.BasePageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author jiewus
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统角色查询参数")
public class RolePageParam extends BasePageParam {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "角色id")
  private String roleId;

  @Schema(description = "角色名称")
  private String roleName;

  @Schema(description = "角色类型")
  private String roleCode;

  @Schema(description = "角色状态，0：禁用，1：启用")
  private StatusEnum status;

}
