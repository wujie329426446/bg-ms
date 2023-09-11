package com.bg.system.param.sysrole;

import com.bg.commons.pagination.BasePageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 系统角色 查询参数对象
 * </pre>
 *
 * @author jiewus
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统角色查询参数")
public class SysRolePageParam extends BasePageParam {

  @Serial
  private static final long serialVersionUID = 5068991832024325736L;

  @Schema(description = "角色名称")
  private String name;

  @Schema(description = "角色编码")
  private String code;

  @Schema(description = "角色状态，0：禁用，1：启用")
  private Integer state;
}
