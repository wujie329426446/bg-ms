package com.bg.system.vo;

import com.bg.commons.enums.StatusEnum;
import com.bg.system.enums.MenuLevelEnum;
import com.bg.system.enums.MenuTypeEnum;
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
 * 系统权限 查询结果对象
 * </pre>
 *
 * @author jiewus
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "系统权限查询参数")
public class SysMenuVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "主键")
  private String id;

  @Schema(description = "权限名称")
  private String menuName;

  @Schema(description = "父id")
  private String parentId;

  @Schema(description = "路径")
  private String routePath;

  @Schema(description = "唯一编码")
  private String code;

  @Schema(description = "图标")
  private String icon;

  @Schema(description = "类型，1：目录，2：菜单，3：按钮")
  private MenuTypeEnum type;

  @Schema(description = "层级，1：第一级，2：第二级，N：第N级")
  private MenuLevelEnum level;

  @Schema(description = "状态，0：禁用，1：启用")
  private StatusEnum status;

  @Schema(description = "排序")
  private Integer sort;

  @Schema(description = "备注")
  private String remark;

  @Schema(description = "组件")
  private String component;

  @Schema(description = "isShow")
  private Integer isShow;

  @Schema(description = "keepAlive")
  private Integer keepAlive;

  @Schema(description = "isExt")
  private Integer isExt;

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