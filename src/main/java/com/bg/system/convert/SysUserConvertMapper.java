package com.bg.system.convert;

import com.bg.commons.convert.BaseConvertMapper;
import com.bg.system.entity.SysUser;
import com.bg.system.vo.SysUserVo;
import org.mapstruct.Mapper;

/**
 * 系统用户vo转换类
 *
 * @author jiewus
 * @since 2023/09/11
 */
@Mapper(componentModel = "spring")
public interface SysUserConvertMapper extends BaseConvertMapper<SysUserVo, SysUser> {

}
