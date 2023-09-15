package com.bg.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bg.system.entity.SysLog;
import com.bg.system.param.LogPageParam;
import com.bg.system.vo.SysLogVo;

/**
 * 系统日志service
 *
 * @author jiewus
 * @since 2023/09/14
 */
public interface ISysLogService extends IService<SysLog> {

  /**
   * 根据id查询日志对象
   *
   * @param id
   * @return 日志对象
   */
  SysLogVo getLogById(String id);

  /**
   * 获取分页对象
   *
   * @param pageParam
   * @return
   * @throws Exception
   */
  Page<SysLog> getLogPageList(LogPageParam pageParam);
}
