package com.bg.system.vo;

import com.bg.commons.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 部门TreeVo
 * </pre>
 *
 * @author jiewus
 */
@Data
@Accessors(chain = true)
@Schema(description = "部门")
public class SysDeptTreeVo implements Serializable {

  @Serial
  private static final long serialVersionUID = -2250233632748939400L;

  @Schema(description = "主键")
  private String id;

  @Schema(description = "部门名称")
  private String deptName;

  @Schema(description = "部门编码")
  private String deptCode;

  @Schema(description = "父id")
  private String parentId;

  @Schema(description = "状态，0：禁用，1：启用")
  private StatusEnum status;

  @Schema(description = "排序")
  private Integer sort;

  @Schema(description = "备注")
  private String remark;

  private List<SysDeptTreeVo> children;

}
