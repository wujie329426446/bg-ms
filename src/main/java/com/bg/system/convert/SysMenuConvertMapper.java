package com.bg.system.convert;

import com.bg.commons.convert.BaseConvertMapper;
import com.bg.system.entity.SysMenu;
import com.bg.system.vo.SysMenuVo;
import org.mapstruct.Mapper;

/**
 * SysPermission类对象书香转换器
 * / *
 *
 * @author jiewus
 **/
@Mapper(componentModel = "spring")
public interface SysMenuConvertMapper extends BaseConvertMapper<SysMenuVo, SysMenu> {

}
