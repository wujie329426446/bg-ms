package com.bg.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

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

  private static final long serialVersionUID = 1L;

  @Schema(description = "主键")
  private String id;

  @Schema(description = "角色id")
  private String roleId;

  @Schema(description = "权限id")
  private String permissionId;

  @Schema(description = "状态，0：禁用，1：启用")
  private Integer state;

  @Schema(description = "备注")
  private String remark;

  @Schema(description = "版本")
  private Integer version;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Schema(description = "创建时间")
  private LocalDateTime createTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Schema(description = "修改时间")
  private LocalDateTime updateTime;

}