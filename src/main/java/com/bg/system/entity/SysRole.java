package com.bg.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bg.commons.core.validator.groups.Add;
import com.bg.commons.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统角色")
@TableName("sys_role")
public class SysRole extends BaseEntity<SysRole> {

  @Serial
  private static final long serialVersionUID = -487738234353456553L;

  @Schema(description = "角色名称")
  @TableField("role_name")
  @NotBlank(message = "角色名称不能为空", groups = {Add.class})
  private String roleName;

  @Schema(description = "角色唯一编码")
  @TableField("role_code")
  private String roleCode;

  @Schema(description = "角色类型")
  @TableField("type")
  private Integer type;

}
