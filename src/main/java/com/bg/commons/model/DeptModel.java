package com.bg.commons.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 部门模型
 *
 * @author jiewus
 */
@Data
public class DeptModel implements Serializable {

  @Serial
  private static final long serialVersionUID = -118874730758289766L;

  @Schema(description = "机构id")
  private String deptId;

  @Schema(description = "父部门id")
  private String parentId;

  @Schema(description = "祖级列表")
  private String ancestors;

  @Schema(description = "部门名称")
  private String deptName;

  @Schema(description = "显示顺序")
  private Integer sort;

  @Schema(description = "部门状态")
  private Integer status;

  @Schema(description = "删除标志")
  private Integer deleted;

  @Schema(description = "子部门")
  private List<DeptModel> children;

}
