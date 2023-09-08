package com.bg.system.controller;

import com.bg.commons.api.ApiResult;
import com.bg.commons.controller.BaseController;
import com.bg.commons.core.validator.groups.Add;
import com.bg.commons.core.validator.groups.Update;
import com.bg.commons.log.annotation.OperationLog;
import com.bg.commons.log.enums.OperationLogType;
import com.bg.commons.pagination.Paging;
import com.bg.system.dto.SysUserDto;
import com.bg.system.entity.SysUser;
import com.bg.system.param.UserPageParam;
import com.bg.system.service.SysUserService;
import com.bg.system.vo.SysUserQueryVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户 前端控制器
 *
 * @author jiewus
 */
@Slf4j
@RestController
@Tag(name = "系统用户API")
@RequestMapping("/v1/api/admin/auth/user")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysUserController extends BaseController<SysUser, SysUserService, SysUser> {

  /**
   * 添加系统用户
   */
  @PostMapping("/add")
  @PreAuthorize("@auth.hasPermission('sys:user:add')")
  @OperationLog(name = "添加系统用户", type = OperationLogType.ADD)
  @Operation(summary = "添加系统用户")
  public ApiResult<String> addSysUser(@Validated(Add.class) @RequestBody SysUserDto sysUserDto) throws Exception {
    baseService.addUser(sysUserDto);
    return ApiResult.success("创建成功");
  }

  /**
   * 修改系统用户
   */
  @PostMapping("/update")
  @PreAuthorize("@auth.hasPermission('sys:user:update')")
  @OperationLog(name = "修改系统用户", type = OperationLogType.UPDATE)
  @Operation(summary = "修改系统用户")
  public ApiResult<Boolean> updateSysUser(@Validated(Update.class) @RequestBody SysUserDto sysUserDto) throws Exception {
    boolean flag = baseService.updateUser(sysUserDto);
    return ApiResult.result(flag);
  }

  /**
   * 系统用户分页列表
   */
  @GetMapping("/getPageList")
  @PreAuthorize("@auth.hasPermission('sys:user:page')")
  @OperationLog(name = "系统用户分页列表", type = OperationLogType.PAGE)
  @Operation(summary = "系统用户分页列表")
  public ApiResult<Paging<SysUserQueryVo>> getSysUserPageList(@Validated UserPageParam sysUserPageParam) throws Exception {
    Paging<SysUserQueryVo> paging = baseService.getUserPageList(sysUserPageParam);
    return ApiResult.success(paging);
  }

  /**
   * 删除系统用户
   */
  @PostMapping("/delete/{id}")
  @PreAuthorize("@auth.hasPermission('sys:user:delete')")
  @OperationLog(name = "删除系统用户", type = OperationLogType.DELETE)
  @Operation(summary = "删除系统用户")
  public ApiResult<Boolean> deleteSysUser(@PathVariable("id") String id) throws Exception {
    boolean flag = baseService.deleteSysUser(id);
    return ApiResult.result(flag);
  }

}
