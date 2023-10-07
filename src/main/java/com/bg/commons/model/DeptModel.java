package com.bg.commons.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.bg.commons.core.validator.groups.Add;
import com.bg.commons.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
  private String id;

  @Schema(description = "父部门id")
  private String parentId;

  @Schema(description = "祖级列表")
  private String ancestors;

  @Schema(description = "部门名称")
  private String deptName;

  @Schema(description = "部门编码")
  private String deptCode;

  @Schema(description = "层级，1：第一级，2：第二级，N：第N级")
  private Integer level;

  @Schema(description = "层级编码，部门名称|部门名称|部门名称")
  private String levelCode;

  @Schema(description = "显示顺序")
  private Integer sort;

  @Schema(description = "部门状态")
  private StatusEnum status;

  @Schema(description = "子部门")
  private List<DeptModel> children;

}
