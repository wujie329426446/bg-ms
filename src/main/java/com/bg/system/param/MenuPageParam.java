package com.bg.system.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.bg.commons.pagination.BasePageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 系统权限 查询参数对象
 * </pre>
 *
 * @author jiewus
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统权限查询参数")
public class MenuPageParam extends BasePageParam {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "菜单名称")
  private String menuName;

  @Schema(description = "唯一编码")
  private String code;

}
