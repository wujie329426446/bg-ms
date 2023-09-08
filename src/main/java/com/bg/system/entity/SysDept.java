package com.bg.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bg.commons.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 部门
 * </pre>
 *
 * @author jiewus
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "部门")
@TableName("sys_dept")
public class SysDept extends BaseEntity<SysDept> {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "部门名称")
  @TableField("dept_name")
  private String deptName;

  @Schema(description = "部门编码")
  @TableField("dept_code")
  private String deptCode;

  @Schema(description = "父id")
  @TableField("parent_id")
  private String parentId;

  @Schema(description = "层级，1：第一级，2：第二级，N：第N级")
  @TableField("level")
  private Integer level;

  @Schema(description = "层级编码，部门编码|部门编码|部门编码")
  @TableField("level_code")
  private String levelCode;

  @Schema(description = "排序")
  @TableField("sort")
  private Integer sort;

}
