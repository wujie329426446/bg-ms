package com.bg.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author jiewus
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouteItemVO implements Serializable {

  @Schema(description = "菜单路径")
  private String routePath;

  @Schema(description = "组件")
  private String component;

  @Schema(description = "Route Meto")
  private RouteMetoVO meta;

  @Schema(description = "路由")
  private String name;

  @Schema(description = "别名")
  private String alias;

  @Schema(description = "转发redirect")
  private String redirect;

  @Schema(description = "区分大小写")
  private Boolean caseSensitive;

  @Schema(description = "菜单子集")
  private List<RouteItemVO> children;

}
