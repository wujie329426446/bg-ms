package com.bg.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bg.system.entity.SysLog;
import com.bg.system.param.SysLogQuery;
import com.bg.system.vo.SysLogInfoVo;
import com.bg.system.vo.SysLogVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统日志 Mapper 接口
 *
 * @author jiewus
 */
public interface SysLogMapper extends BaseMapper<SysLog> {

  /**
   * 系统日志详情
   *
   * @param id
   * @return
   */
  SysLogInfoVo getSysLogById(String id);

  /**
   * 系统日志分页列表
   *
   * @param sysLogQuery
   * @return
   */
  List<SysLogVo> getSysLogList(SysLogQuery sysLogQuery);

}
