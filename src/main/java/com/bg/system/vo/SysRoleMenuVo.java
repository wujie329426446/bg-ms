package com.bg.system.vo;

import com.bg.commons.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 角色权限关系 查询结果对象
 * </pre>
 *
 * @author jiewus
 */
@Data
@Accessors(chain = true)
@Schema(description = "角色权限关系查询参数")
public class SysRoleMenuVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "主键")
  private String id;

  @Schema(description = "角色id")
  private String roleId;

  @Schema(description = "权限id")
  private String permissionId;

  @Schema(description = "状态，0：禁用，1：启用")
  private StatusEnum state;

  @Schema(description = "备注")
  private String remark;

}