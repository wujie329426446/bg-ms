package com.bg.system.param;

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
@Schema(description = "用户列表参数")
public class RolePageParam extends BasePageParam {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "搜索词")
  private String criteria;

  @Schema(description = "项目")
  private String projectType;

  private Integer userId;

  private Integer roleType;

  private Integer userType;

}
