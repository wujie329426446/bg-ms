package com.bg.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bg.commons.core.validator.groups.Add;
import com.bg.commons.entity.BaseEntity;
import com.bg.system.enums.MenuLevelEnum;
import com.bg.system.enums.MenuTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 系统权限
 * </pre>
 *
 * @author jiewus
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "菜单权限")
@TableName("sys_menu")
public class SysMenu extends BaseEntity<SysMenu> {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "菜单名称")
  @TableField("menu_name")
  @NotBlank(message = "菜单名称不能为空", groups = {Add.class})
  private String menuName;

  @Schema(description = "父id")
  @TableField("parent_id")
  private String parentId;

  @Schema(description = "路径")
  @TableField("route_path")
  private String routePath;

  @Schema(description = "唯一编码")
  @TableField("code")
  private String code;

  @Schema(description = "图标")
  @TableField("icon")
  private String icon;

  @Schema(description = "类型，1：目录，2：菜单，3：按钮")
  @NotNull(message = "类型，0：目录，1：菜单，2：按钮 不能为空", groups = {Add.class})
  @TableField("type")
  private MenuTypeEnum type;

  @Schema(description = "层级，1：第一级，2：第二级，N：第N级")
  @TableField("level")
  private MenuLevelEnum level;

  @Schema(description = "排序")
  @TableField("sort")
  private Integer sort;


  @Schema(description = "组件")
  @TableField("component")
  private String component;

  @Schema(description = "是否展示")
  @TableField("is_show")
  private Integer isShow;

  @TableField("keep_alive")
  private Integer keepAlive;

  @TableField("is_ext")
  private Integer isExt;

  @TableField("frame")
  private Integer frame;

}
