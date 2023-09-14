package com.bg.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bg.commons.api.ApiResult;
import com.bg.commons.constant.LoginConstant;
import com.bg.commons.controller.BaseController;
import com.bg.commons.core.validator.groups.Add;
import com.bg.commons.core.validator.groups.Query;
import com.bg.commons.core.validator.groups.Update;
import com.bg.commons.log.annotation.OperationLog;
import com.bg.commons.log.enums.OperationLogType;
import com.bg.framework.prefix.AdminApiRestController;
import com.bg.system.entity.SysDept;
import com.bg.system.param.DeptPageParam;
import com.bg.system.param.RolePageParam;
import com.bg.system.service.ISysDeptService;
import com.bg.system.vo.SysDeptTreeVo;
import com.bg.system.vo.SysDeptVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <pre>
 * 部门 前端控制器
 * </pre>
 *
 * @author jiewus
 */
@Slf4j
@AdminApiRestController
@RequestMapping("/dept")
@Tag(name = "系统部门API")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysDeptController extends BaseController<SysDept, ISysDeptService, DeptPageParam> {

  @PostMapping("/add")
  @PreAuthorize("@auth.hasPermission('sys:department:add')")
  @OperationLog(name = "添加部门", type = OperationLogType.ADD)
  @Operation(
      summary = "添加部门",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "请求体描述",
          required = true,
          content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = SysDept.class)
              )
          }
      ),
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<Boolean> save(@Validated(Add.class) @RequestBody SysDept sysDept) {
    boolean flag = baseService.save(sysDept);
    return ApiResult.result(flag);
  }

  @PostMapping("/update")
  @PreAuthorize("@auth.hasPermission('sys:department:update')")
  @OperationLog(name = "修改部门", type = OperationLogType.UPDATE)
  @Operation(
      summary = "修改部门",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "请求体描述",
          required = true,
          content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = SysDept.class)
              )
          }
      ),
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<Boolean> updateById(@Validated(Update.class) @RequestBody SysDept sysDept) {
    boolean flag = baseService.updateById(sysDept);
    return ApiResult.result(flag);
  }

  @GetMapping("/detail")
  @PreAuthorize("@auth.hasPermission('sys:department:info')")
  @OperationLog(name = "部门详情", type = OperationLogType.INFO)
  @Operation(
      summary = "部门详情",
      parameters = {
          @Parameter(
              description = "主键id",
              in = ParameterIn.QUERY,
              name = "id",
              required = true
          )
      },
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Success",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(
                      title = "ApiResult、SysDeptVo组合模型",
                      description = "返回实体，ApiResult内data为SysDeptVo类型的对象",
                      anyOf = {ApiResult.class, SysDeptVo.class}
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<SysDeptVo> getDeptById(@RequestParam String id) {
    SysDeptVo sysDeptVo = baseService.getDeptById(id);
    return ApiResult.success(sysDeptVo);
  }

  @DeleteMapping("/delete/{id}")
  @PreAuthorize("@auth.hasPermission('sys:department:delete')")
  @OperationLog(name = "删除部门及子部门(校验部门及下级部门用户)", type = OperationLogType.DELETE)
  @Operation(
      summary = "删除部门及子部门(校验部门及下级部门用户)",
      parameters = {
          @Parameter(
              description = "主键id",
              in = ParameterIn.PATH,
              name = "id",
              required = true
          )
      },
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Success",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(
                      title = "ApiResult模型",
                      description = "返回实体，ApiResult内data为操作结果",
                      example = "操作成功",
                      anyOf = {ApiResult.class, String.class}
                  )
              )
          ),
          @ApiResponse(
              responseCode = "500",
              description = "Fail",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(
                      title = "ApiResult模型",
                      description = "返回实体，ApiResult内data为操作结果",
                      example = "<%s>部门下有关联用户,无法删除",
                      anyOf = {ApiResult.class, String.class}
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<String> deleteDept(@PathVariable("id") String id) {
    ApiResult<String> result = baseService.deleteDept(id);
    return result;
  }

  @PostMapping("/getPageList")
  @PreAuthorize("@auth.hasPermission('sys:department:page')")
  @OperationLog(name = "部门分页列表", type = OperationLogType.PAGE)
  @Operation(
      summary = "部门分页列表",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "请求体描述",
          required = true,
          content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = DeptPageParam.class)
              )
          }
      ),
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Success",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(
                      title = "ApiResult、Page、SysDept组合模型",
                      description = "返回实体，ApiResult内data为SysDept类型的Page对象",
                      anyOf = {ApiResult.class, Page.class, SysDept.class}
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<Page<SysDept>> getDeptPageList(@Validated(Query.class) @RequestBody DeptPageParam pageParam) {
    Page<SysDept> page = baseService.getDeptPageList(pageParam);
    return ApiResult.success(page);
  }


  @PostMapping("/getList")
  @PreAuthorize("@auth.hasPermission('sys:department:list')")
  @OperationLog(name = "获取部门列表", type = OperationLogType.LIST)
  @Operation(
      summary = "获取部门列表",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "请求体描述",
          required = true,
          content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = RolePageParam.class)
              )
          }
      ),
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Success",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(
                      title = "ApiResult、SysDeptVo",
                      description = "返回实体，ApiResult内data为SysDeptVo集合",
                      anyOf = {ApiResult.class, SysDeptVo.class}
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<List<SysDeptVo>> getDeptList(@Validated(Query.class) @RequestBody DeptPageParam pageParam) {
    List<SysDeptVo> list = baseService.getDeptList(pageParam);
    return ApiResult.success(list);
  }

  @GetMapping("/getTreeList")
  @PreAuthorize("@auth.hasPermission('sys:department:tree')")
  @OperationLog(name = "获取树形列表", type = OperationLogType.OTHER_QUERY)
  @Operation(
      summary = "获取树形列表",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Success",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(
                      title = "ApiResult、SysDeptTreeVo",
                      description = "返回实体，ApiResult内data为SysDeptTreeVo集合",
                      anyOf = {ApiResult.class, SysDeptTreeVo.class}
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<List<SysDeptTreeVo>> getDeptTreeList() {
    List<SysDeptTreeVo> treeVos = baseService.getDeptTreeList();
    return ApiResult.success(treeVos);
  }

}

