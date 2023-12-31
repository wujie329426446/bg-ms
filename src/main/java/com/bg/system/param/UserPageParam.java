package com.bg.system.param;

import com.bg.commons.enums.StatusEnum;
import com.bg.commons.pagination.BasePageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.util.Date;
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
public class UserPageParam extends BasePageParam {

  @Serial
  private static final long serialVersionUID = 7437881671144580610L;

  @Schema(description = "部门id")
  private String deptId;

  @Schema(description = "角色id")
  private String roleId;

  @Schema(description = "状态，0：禁用，1：启用，2：锁定")
  private StatusEnum status;

  @Schema(description = "创建时间开始")
  private Date createTimeStart;

  @Schema(description = "创建时间结束")
  private Date createTimeEnd;

  @Schema(description = "用户名")
  private String username;

  @Schema(description = "昵称")
  private String nickname;

}
