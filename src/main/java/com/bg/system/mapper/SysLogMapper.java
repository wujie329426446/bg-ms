package com.bg.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bg.system.entity.SysLog;
import com.bg.system.param.LogPageParam;
import com.bg.system.vo.SysLogInfoVo;
import com.bg.system.vo.SysLogVo;

import java.util.List;

/**
 * 系统日志 Mapper 接口
 *
 * @author jiewus
 */
public interface SysLogMapper extends BaseMapper<SysLog> {

  /**
   * 系统日志分页列表
   *
   * @param logPageParam
   * @return
   */
  List<SysLogVo> getSysLogList(LogPageParam logPageParam);

}
