package com.bg.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author jiewus
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouteMetoVO implements Serializable {

  @Schema(description = "标题")
  private String title;

  @Schema(description = "忽略权限")
  private Boolean ignoreAuth;

  @Schema(description = "角色")
  private String roles;

  private Boolean ignoreKeepAlive;

  private Boolean affix;

  private String icon;

  private String frameSrc;

  private String transitionName;

  private Boolean hideBreadcrumb;

  private Boolean hideChildrenInMenu;

  private Boolean carryParam;

  private Boolean single;

  private String currentActiveMenu;

  private Boolean hideTab;

  private Boolean hideMenu;

  private Boolean isLink;

}
