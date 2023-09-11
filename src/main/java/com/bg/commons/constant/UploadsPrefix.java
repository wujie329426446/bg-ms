package com.bg.commons.constant;

/**
 * 文件上传路径
 *
 * @author Tang
 */
public interface UploadsPrefix {

  /**
   * 文件上传路径
   */
  String UPLOADS = "/uploads";

  /**
   * 头像上传路径前缀
   */
  String AVATAR_PREFIX = "/avatar";

  /**
   * 头像上传路径
   */
  String AVATAR = UPLOADS + AVATAR_PREFIX;

}
