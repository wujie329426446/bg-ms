package com.bg.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.ToString;
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
@ToString(callSuper = true)
@Schema(description = "部门")
public class SysDeptVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "部门id")
  private String id;

  @Schema(description = "部门名称")
  private String deptName;

  @Schema(description = "部门编码")
  private String deptCode;

  @Schema(description = "父id")
  private String parentId;

  @Schema(description = "状态，0：禁用，1：启用")
  private Integer status;

  @Schema(description = "排序")
  private Integer sort;

  @Schema(description = "备注")
  private String remark;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Schema(description = "创建时间")
  private LocalDateTime createTime;

  @Schema(description = "创建人姓名")
  private String creatorName;

  @Schema(description = "修改人姓名")
  private String updateName;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Schema(description = "修改时间")
  private LocalDateTime updateTime;
}
