package com.bg.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bg.commons.api.ApiResult;
import com.bg.commons.constant.LoginConstant;
import com.bg.commons.core.validator.groups.Query;
import com.bg.commons.log.annotation.OperationLog;
import com.bg.commons.log.enums.OperationLogType;
import com.bg.framework.prefix.AdminApiRestController;
import com.bg.system.entity.SysLog;
import com.bg.system.param.LogPageParam;
import com.bg.system.service.ISysLogService;
import com.bg.system.vo.SysLogVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@AdminApiRestController
@RequestMapping("/log")
@Tag(name = "系统日志API")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysLogController {

  private final ISysLogService baseService;

  @GetMapping("/detail")
  @PreAuthorize("@auth.hasPermission('sys:operation:log:page')")
  @OperationLog(name = "操作日志详情页面查看", type = OperationLogType.INFO)
  @Operation(
      summary = "操作日志详情页面查看",
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
                      title = "ApiResult、SysLogVo组合模型",
                      description = "返回实体，ApiResult内data为SysLogVo类型的对象",
                      anyOf = {ApiResult.class, SysLogVo.class}
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<SysLogVo> getLogById(@RequestParam String id) {
    SysLogVo sysLogVo = baseService.getLogById(id);
    return ApiResult.success(sysLogVo);
  }


  @PostMapping("/getPageList")
  @PreAuthorize("@auth.hasPermission('sys:operation:log:page')")
  @OperationLog(name = "系统日志分页列表", type = OperationLogType.PAGE)
  @Operation(
      summary = "系统日志分页列表",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "请求体描述",
          required = true,
          content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = LogPageParam.class)
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
                      title = "ApiResult、Page、SysRoleVo",
                      description = "返回实体，ApiResult内data为SysRoleVo类型的Page对象",
                      anyOf = {ApiResult.class, Page.class, SysLog.class}
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<Page<SysLog>> getLogPageList(@Validated(Query.class) @RequestBody LogPageParam pageParam) {
    Page<SysLog> page = baseService.getLogPageList(pageParam);
    return ApiResult.success(page);
  }


}
