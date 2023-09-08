package com.bg.commons.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 角色VO
 *
 * @author jiewus
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleInfoVO implements Serializable {

  private String roleName;

  private String value;

  private String id;

}
