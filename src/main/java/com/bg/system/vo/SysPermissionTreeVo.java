package com.bg.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统权限树形列表VO
 *
 * @author jiewus
 **/
@Data
@Accessors(chain = true)
@Schema(description = "系统权限树形列表")
public class SysPermissionTreeVo implements Serializable {

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

  @Schema(description = "类型，1：菜单，2：按钮")
  private Integer type;

  @Schema(description = "层级，1：第一级，2：第二级，N：第N级")
  private Integer level;

  @Schema(description = "状态，0：禁用，1：启用")
  private Integer state;

  @Schema(description = "排序")
  private Integer sort;

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

  @Schema(description = "子节点集合")
  private List<SysPermissionTreeVo> children;

  @Schema(description = "组件")
  private String component;

  private Integer isShow;
  private Integer keepAlive;
  private Integer isExt;
}