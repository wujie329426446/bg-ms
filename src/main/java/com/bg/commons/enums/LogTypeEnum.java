package com.bg.commons.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统日志类型
 * 0:访问日志,1:新增,2:修改,3:删除,4:详情,5:所有列表,6:分页列表,7:其它查询,8:上传文件,9:登录,10:退出
 *
 * @author jiewus
 **/
@Getter
@AllArgsConstructor
public enum LogTypeEnum implements BaseEnum {

  OTHER(0, "其它"),

  ADD(1, "新增"),

  UPDATE(2, "修改"),

  DELETE(3, "删除"),

  INFO(4, "详情查询"),

  ALL_LIST(5, "所有列表"),

  LIST(6, "分页列表"),

  OTHER_QUERY(7, "其它查询"),

  UPLOAD(8, "上传文件");

  @JsonValue
  @EnumValue
  private Integer code;
  private String desc;

}
