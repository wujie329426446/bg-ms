package com.bg.system.convert;

import com.bg.commons.convert.BaseConvertMapper;
import com.bg.system.entity.SysRole;
import com.bg.system.vo.SysRoleVo;
import org.mapstruct.Mapper;

/**
 * 系统角色vo转换类
 *
 * @author jiewus
 * @since 2023/09/11
 */
@Mapper(componentModel = "spring")
public interface SysRoleConvertMapper extends BaseConvertMapper<SysRoleVo, SysRole> {

}
