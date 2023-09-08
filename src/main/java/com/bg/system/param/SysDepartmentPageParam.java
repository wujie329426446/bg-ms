package com.bg.system.param;

import com.bg.commons.pagination.BasePageOrderParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 部门 查询参数对象
 * </pre>
 *
 * @author jiewus
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "部门查询参数")
public class SysDepartmentPageParam extends BasePageOrderParam {

  private static final long serialVersionUID = 1L;
}