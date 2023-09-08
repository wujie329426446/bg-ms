package com.bg.system.vo;

import com.bg.commons.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 系统用户 查询结果对象
 * </pre>
 *
 * @author jiewus
 */
@Data
@Accessors(chain = true)
@Schema(description = "系统用户查询参数")
public class SysUserQueryVo implements Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "主键")
  private String id;

  @Schema(description = "用户名")
  private String username;

  @Schema(description = "昵称")
  private String nickname;

  @Schema(description = "真名")
  private String realname;

  @Schema(description = "手机号码")
  private String phone;

  @Schema(description = "性别，0：女，1：男，默认1")
  private GenderEnum gender;

  @Schema(description = "头像")
  private String avatar;

  @Schema(description = "remark")
  private String remark;

  @Schema(description = "状态，0：禁用，1：启用，2：锁定")
  private Integer state;

  @Schema(description = "部门id")
  private String deptId;

  @Schema(description = "角色组")
  private List<String> roleIds;

  @Schema(description = "逻辑删除，0：未删除，1：已删除")
  private Integer deleted;

  @Schema(description = "版本")
  private Integer version;

  @Schema(description = "创建时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createTime;

  @Schema(description = "创建人姓名")
  private String creatorName;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Schema(description = "修改人姓名")
  private String updateName;

  @Schema(description = "修改时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateTime;

  @Schema(description = "部门名称")
  private String deptName;

  @Schema(description = "角色名称")
  private String roleName;

  private String email;

}