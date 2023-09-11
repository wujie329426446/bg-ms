package com.bg.commons.model;

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
public class RoleModel implements Serializable {

  private String roleName;

  private String value;

  private String id;

}
