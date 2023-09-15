package com.bg.system.param;

import com.bg.commons.enums.StatusEnum;
import com.bg.commons.pagination.BasePageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 部门 查询参数对象
 * </pre>
 *
 * @author jiewus
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "部门查询参数")
public class DeptPageParam extends BasePageParam {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "部门名称")
  private String deptName;

  @Schema(description = "部门编码")
  private String deptCode;

  @Schema(description = "父id")
  private String parentId;

  @Schema(description = "状态，0：禁用，1：启用")
  private StatusEnum status;


}
