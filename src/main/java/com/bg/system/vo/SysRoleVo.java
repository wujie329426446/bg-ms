package com.bg.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 系统角色 查询结果对象
 * </pre>
 *
 * @author jiewus
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "系统角色查询参数")
public class SysRoleVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "主键")
  private String id;

  @Schema(description = "角色名称")
  private String roleName;

  @Schema(description = "角色唯一编码")
  private String roleCode;

  @Schema(description = "角色类型")
  private Integer type;

  @Schema(description = "角色状态，0：禁用，1：启用")
  private Integer status;

  @Schema(description = "备注")
  private String remark;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Schema(description = "创建时间")
  private LocalDateTime createTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Schema(description = "修改时间")
  private LocalDateTime updateTime;

  @Schema(description = "权限集合")
  private List<String> permissions;

}