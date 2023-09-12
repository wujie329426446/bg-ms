package com.bg.commons.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.bg.commons.core.validator.groups.Update;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author jiewus
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "BaseEntity对象，通用字段")
public abstract class BaseEntity<T extends Model<?>> extends Model<T> implements Serializable {

  @Serial
  private static final long serialVersionUID = -7176390653391227433L;

  @NotBlank(message = "修改操作id不能为空", groups = {Update.class})
  @Schema(description = "主键ID")
  @TableId(value = "id", type = IdType.ASSIGN_UUID)
  private String id;

  @Schema(description = "版本（版本不用传）", hidden = true)
  @Null(message = "版本不用传")
  @Version
  private Integer version;

  @Schema(description = "创建时间", hidden = true)
  @TableField(fill = FieldFill.INSERT)
  @Null(message = "创建时间不用传")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createTime;

  @Schema(description = "创建人", hidden = true)
  @TableField(value = "creator_id", fill = FieldFill.INSERT)
  private Long creatorId;

  @Schema(description = "创建人姓名", hidden = true)
  @TableField(value = "creator_name", fill = FieldFill.INSERT)
  private String creatorName;

  @Schema(description = "修改时间", hidden = true)
  @TableField(fill = FieldFill.INSERT_UPDATE)
  @Null(message = "修改时间不用传")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateTime;

  @Schema(description = "修改人", hidden = true)
  @TableField(value = "update_id", fill = FieldFill.INSERT_UPDATE)
  private Integer updateId;

  @Schema(description = "修改人姓名", hidden = true)
  @TableField(value = "update_name", fill = FieldFill.INSERT_UPDATE)
  private String updateName;

  @Schema(description = "备注")
  @TableField(value = "remark")
  private String remark;

  @Schema(description = "状态，0：禁用，1：启用")
  @TableField("status")
  private Integer status;

  @Schema(description = "逻辑删除", hidden = true)
  @TableField("deleted")
  // 逻辑删除 默认效果 0 没有删除 1 已经删除
  @TableLogic
  private Integer deleted;
}
