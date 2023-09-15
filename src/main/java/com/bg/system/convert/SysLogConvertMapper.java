package com.bg.system.convert;

import com.bg.commons.convert.BaseConvertMapper;
import com.bg.system.entity.SysLog;
import com.bg.system.vo.SysLogVo;
import org.mapstruct.Mapper;

/**
 * 系统日志vo转换类
 *
 * @author jiewus
 * @since 2023/09/14
 */
@Mapper(componentModel = "spring")
public interface SysLogConvertMapper extends BaseConvertMapper<SysLogVo, SysLog> {

}
