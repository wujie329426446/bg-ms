package com.bg.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bg.commons.enums.LogTypeEnum;
import com.bg.system.convert.SysLogConvertMapper;
import com.bg.system.entity.SysLog;
import com.bg.system.mapper.SysLogMapper;
import com.bg.system.param.LogPageParam;
import com.bg.system.service.ISysLogService;
import com.bg.system.vo.SysLogVo;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ProjectName: bg-ms
 * @Author: jiewus
 * @Description: 系统日志 service实现
 * @Date: 2023/9/14 17:12
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

  private final SysLogConvertMapper sysLogConvertMapper;

  @Override
  public SysLogVo getLogById(String id) {
    SysLog sysLog = super.getById(id);
    SysLogVo sysLogVo = sysLogConvertMapper.toDto(sysLog);
    return sysLogVo;
  }

  @Override
  public Page<SysLog> getLogPageList(LogPageParam pageParam) {
    LambdaQueryWrapper<SysLog> queryWrapper = Wrappers.lambdaQuery(SysLog.class);
    String keyword = pageParam.getKeyword();
    String traceId = pageParam.getTraceId();
    String moduleName = pageParam.getModuleName();
    String requestUrl = pageParam.getRequestUrl();
    String logName = pageParam.getLogName();
    String username = pageParam.getUsername();
    LogTypeEnum logType = pageParam.getLogType();
    Boolean responseSuccess = pageParam.getResponseSuccess();
    String requestIp = pageParam.getRequestIp();
    LocalDateTime startTime = pageParam.getCreateStartTime();
    LocalDateTime endTime = pageParam.getCreateEndTime();

    // 条件查询
    queryWrapper
        .and(StringUtils.isNotBlank(keyword),
            i -> i.eq(SysLog::getTraceId, keyword)
                .or().like(SysLog::getModuleName, keyword)
                .or().like(SysLog::getRequestUrl, keyword)
                .or().like(SysLog::getLogName, keyword)
                .or().like(SysLog::getUserName, keyword)
                .or().like(SysLog::getLogType, keyword)
                .or().like(SysLog::getRequestIp, keyword)
        )
        .like(StringUtils.isNotBlank(traceId), SysLog::getTraceId, traceId)
        .like(StringUtils.isNotBlank(moduleName), SysLog::getModuleName, moduleName)
        .like(StringUtils.isNotBlank(requestUrl), SysLog::getRequestUrl, requestUrl)
        .like(StringUtils.isNotBlank(logName), SysLog::getLogName, logName)
        .like(StringUtils.isNotBlank(username), SysLog::getUserName, username)

        .like(Objects.nonNull(responseSuccess), SysLog::getResponseSuccess, responseSuccess)
        .like(StringUtils.isNotBlank(requestIp), SysLog::getRequestIp, requestIp)
        .between(Objects.nonNull(startTime) && Objects.nonNull(endTime), SysLog::getRequestTime, startTime, endTime);
    if (Objects.nonNull(logType)) {
      queryWrapper.eq(SysLog::getLogType, logType.getCode());
    }

    return super.page(pageParam.getPage(), queryWrapper);
  }
}
