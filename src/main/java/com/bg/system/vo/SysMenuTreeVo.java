package com.bg.system.vo;

import com.bg.commons.enums.StatusEnum;
import com.bg.system.enums.MenuLevelEnum;
import com.bg.system.enums.MenuTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 系统权限树形列表VO
 *
 * @author jiewus
 **/
@Data
@Accessors(chain = true)
@Schema(description = "系统权限树形列表")
public class SysMenuTreeVo implements Serializable {

  private static final long serialVersionUID = 2738804574228359190L;

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

  @Schema(description = "子节点集合")
  private List<SysMenuTreeVo> children;

}