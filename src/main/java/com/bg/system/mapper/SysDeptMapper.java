package com.bg.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bg.system.entity.SysDept;
import java.util.List;
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
   * 获取部门及部门下有关联用户的部门
   *
   * @param levelCode
   * @return
   */
  List<String> getExitsUserDeptName(@Param("levelCode") String levelCode);

}
