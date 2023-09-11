package com.bg.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bg.commons.api.ApiResult;
import com.bg.commons.controller.BaseController;
import com.bg.commons.log.annotation.OperationLog;
import com.bg.commons.log.enums.OperationLogType;
import com.bg.system.entity.SysDept;
import com.bg.system.param.SysDepartmentPageParam;
import com.bg.system.service.SysDeptService;
import com.bg.system.vo.SysDeptTreeVo;
import com.bg.system.vo.SysDeptVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * 部门 前端控制器
 * </pre>
 *
 * @author jiewus
 */
@Slf4j
@RestController
@RequestMapping("/v1/api/admin/sysDepartment")
@Tag(name = "系统部门API")
public class SysDepartmentController extends BaseController<SysDept, SysDeptService, SysDept> {

  /**
   * 添加部门
   */
  @PostMapping("/add")
  @PreAuthorize("@auth.hasPermission('sys:department:add')")
  @OperationLog(name = "添加部门", type = OperationLogType.ADD)
  @Operation(summary = "添加部门")
  public ApiResult<Boolean> addSysDepartment(@Validated @RequestBody SysDept sysDept) throws Exception {
    boolean flag = baseService.saveSysDepartment(sysDept);
    return ApiResult.result(flag);
  }

  /**
   * 修改部门
   */
  @PostMapping("/update")
  @PreAuthorize("@auth.hasPermission('sys:department:update')")
  @OperationLog(name = "修改部门", type = OperationLogType.UPDATE)
  @Operation(summary = "修改部门")
  public ApiResult<Boolean> updateSysDepartment(@Validated @RequestBody SysDept sysDept) throws Exception {
    boolean flag = baseService.updateSysDepartment(sysDept);
    return ApiResult.result(flag);
  }

  /**
   * 删除部门
   */
  @PostMapping("/delete/{id}")
  @PreAuthorize("@auth.hasPermission('sys:department:delete')")
  @OperationLog(name = "删除部门", type = OperationLogType.DELETE)
  @Operation(summary = "删除部门")
  public ApiResult<Boolean> deleteSysDepartment(@PathVariable("id") String id) throws Exception {
    boolean flag = baseService.deleteSysDepartment(id);
    return ApiResult.result(flag);
  }

  /**
   * 获取部门
   */
  @GetMapping("/info/{id}")
  @PreAuthorize("@auth.hasPermission('sys:department:info')")
  @OperationLog(name = "部门详情", type = OperationLogType.INFO)
  @Operation(summary = "部门详情")
  public ApiResult<SysDeptVo> getSysDepartment(@PathVariable("id") String id) throws Exception {
    SysDeptVo sysDeptVo = baseService.getSysDepartmentById(id);
    return ApiResult.success(sysDeptVo);
  }

  /**
   * 部门分页列表
   */
  @PostMapping("/getPageList")
  @PreAuthorize("@auth.hasPermission('sys:department:page')")
  @OperationLog(name = "部门分页列表", type = OperationLogType.PAGE)
  @Operation(summary = "部门分页列表")
  public ApiResult<Page<SysDeptVo>> getSysDepartmentPageList(@Validated @RequestBody SysDepartmentPageParam pageParam) throws Exception {
    Page<SysDeptVo> page = baseService.getSysDepartmentPageList(pageParam);
    return ApiResult.success(page);
  }

  /**
   * 获取所有部门列表
   */
  @PostMapping("/getAllDepartmentList")
  @PreAuthorize("@auth.hasPermission('sys:department:all:list')")
  @OperationLog(name = "获取所有部门的树形列表", type = OperationLogType.OTHER_QUERY)
  @Operation(summary = "获取所有部门的树形列表")
  public ApiResult<List<SysDept>> getAllDepartmentList() throws Exception {
    List<SysDept> list = baseService.getAllDepartmentList();
    return ApiResult.success(list);
  }

  /**
   * 获取所有部门的树形列表
   *
   * @return
   */
  @GetMapping("/getDepartmentTree")
  @PreAuthorize("@auth.hasPermission('sys:department:all:tree')")
  @OperationLog(name = "获取所有部门的树形列表", type = OperationLogType.OTHER_QUERY)
  @Operation(summary = "获取所有部门的树形列表")
  public ApiResult<List<SysDeptTreeVo>> getDepartmentTree() throws Exception {
    List<SysDeptTreeVo> treeVos = baseService.getDepartmentTree();
    return ApiResult.success(treeVos);
  }

  /**
   * 部门列表
   */
  @PostMapping("/getList")
  @PreAuthorize("@auth.hasPermission('sys:department:list')")
  @OperationLog(name = "部门列表", type = OperationLogType.LIST)
  @Operation(summary = "部门列表")
  public ApiResult<List<SysDept>> getSysDepartmentList() throws Exception {
    List<SysDept> list = baseService.list();
    return ApiResult.success(list);
  }

}

