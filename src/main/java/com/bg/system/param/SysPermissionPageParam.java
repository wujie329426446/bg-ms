package com.bg.system.param;

import com.bg.commons.pagination.BasePageOrderParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 系统权限 查询参数对象
 * </pre>
 *
 * @author jiewus
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统权限查询参数")
public class SysPermissionPageParam extends BasePageOrderParam {

  private static final long serialVersionUID = 1L;
}
