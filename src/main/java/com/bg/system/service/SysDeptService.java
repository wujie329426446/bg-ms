package com.bg.system.service;

import com.bg.commons.pagination.Paging;
import com.bg.commons.service.BaseService;
import com.bg.system.entity.SysDept;
import com.bg.system.param.SysDepartmentPageParam;
import com.bg.system.vo.SysDepartmentVo;
import com.bg.system.vo.SysDepartmentTreeVo;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 部门 服务类
 * </pre>
 *
 * @author jiewus
 */
public interface SysDeptService extends BaseService<SysDept> {

  /**
   * 保存
   *
   * @param sysDept
   * @return
   * @throws Exception
   */
  boolean saveSysDepartment(SysDept sysDept) throws Exception;

  /**
   * 修改
   *
   * @param sysDept
   * @return
   * @throws Exception
   */
  boolean updateSysDepartment(SysDept sysDept) throws Exception;

  /**
   * 删除
   *
   * @param id
   * @return
   * @throws Exception
   */
  boolean deleteSysDepartment(String id) throws Exception;

  /**
   * 根据ID获取查询对象
   *
   * @param id
   * @return
   * @throws Exception
   */
  SysDepartmentVo getSysDepartmentById(Serializable id) throws Exception;

  /**
   * 获取分页对象
   *
   * @param sysDepartmentPageParam
   * @return
   * @throws Exception
   */
  Paging<SysDepartmentVo> getSysDepartmentPageList(SysDepartmentPageParam sysDepartmentPageParam) throws Exception;

  /**
   * 根据id校验部门是否存在并且已启用
   *
   * @param id
   * @return
   * @throws Exception
   */
  boolean isEnableSysDepartment(String id) throws Exception;

  /**
   * 获取所有可用的部门列表
   *
   * @return
   */
  List<SysDept> getAllDepartmentList();

  /**
   * 获取所有可用的部门树形列表
   *
   * @return
   */
  List<SysDepartmentTreeVo> getDepartmentTree();

}
