package com.bg.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bg.system.entity.SysDept;
import com.bg.system.param.SysDepartmentPageParam;
import com.bg.system.vo.SysDeptVo;
import org.apache.ibatis.annotations.Param;

/**
 * <pre>
 * 部门 Mapper 接口
 * </pre>
 *
 * @author jiewus
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {


  /**
   * 获取分页对象
   *
   * @param page
   * @param sysDepartmentPageParam
   * @return
   */
  Page<SysDeptVo> getSysDeptPageList(@Param("page") Page page, @Param("param") SysDepartmentPageParam pageParam);

}
