package com.bg.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
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
  SysDeptVo getSysDepartmentById(Serializable id) throws Exception;

  /**
   * 获取分页对象
   *
   * @param pageParam
   * @return
   * @throws Exception
   */
  Page<SysDeptVo> getSysDepartmentPageList(DeptPageParam pageParam) throws Exception;

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
  List<SysDeptTreeVo> getDepartmentTree();

}
