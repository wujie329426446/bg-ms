package com.bg.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bg.commons.api.ApiResult;
import com.bg.system.entity.SysDept;
import com.bg.system.param.DeptPageParam;
import com.bg.system.vo.SysDeptTreeVo;
import com.bg.system.vo.SysDeptVo;
import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 部门 服务类
 * </pre>
 *
 * @author jiewus
 */
public interface ISysDeptService extends IService<SysDept> {

  /**
   * 根据ID获取查询对象
   *
   * @param id
   * @return
   * @throws Exception
   */
  SysDeptVo getDeptById(Serializable id);

  /**
   * 删除部门及子部门(校验部门及下级部门用户)
   *
   * @param id
   * @return ApiResult<String>
   * @throws Exception
   */
  ApiResult<String> deleteDept(Serializable id);

  /**
   * 获取分页对象
   *
   * @param pageParam
   * @return
   * @throws Exception
   */
  Page<SysDept> getDeptPageList(DeptPageParam pageParam);

  /**
   * 获取所有可用的部门列表
   *
   * @return
   */
  List<SysDeptVo> getDeptList(DeptPageParam pageParam);

  /**
   * 获取所有可用的部门树形列表
   *
   * @return
   */
  List<SysDeptTreeVo> getDeptTreeList();

}
