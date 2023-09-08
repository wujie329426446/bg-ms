package com.bg.system.convert;

import com.bg.commons.convert.BaseConvertMapper;
import com.bg.system.entity.SysDept;
import com.bg.system.vo.SysDepartmentTreeVo;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * 部门对象属性转换器
 *
 * @author jiewus
 **/
@Mapper(componentModel = "spring")
public interface SysDepartmentConvertMapper extends BaseConvertMapper<SysDepartmentTreeVo, SysDept> {

}
