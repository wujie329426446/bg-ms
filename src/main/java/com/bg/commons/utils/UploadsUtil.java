package com.bg.commons.utils;

import com.bg.commons.constant.UploadsPrefix;
import com.bg.commons.exception.BusinessException;
import com.bg.config.properties.BgProperties;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * 文件上传工具类
 *
 * @author Tang
 */
public class UploadsUtil {

  private UploadsUtil() {
  }

  private static final BgProperties BG_PROPERTIES = SpringUtil.getBean(BgProperties.class);

  /**
   * 文件名最大长度
   */
  private static final int MAX_FILE_NAME_LENGTH = 128;

  /**
   * 文件最大大小
   */
  private static final long MAX_FILE_SIZE = 2L * 1024L * 1024L;

  /**
   * 获取文件上传路径
   *
   * @return 文件上传路径
   */
  public static String getUploads() {
    return BG_PROPERTIES.getUploadFolder();
  }

  /**
   * 获取头像上传路径
   *
   * @return 头像上传路径
   */
  public static String getAvatar() {
    return getUploads() + UploadsPrefix.AVATAR_PREFIX;
  }

  /**
   * 上传文件
   *
   * @param file 文件
   * @return 文件路径
   */
  public static String upload(MultipartFile file) {
    String filePath = upload(file, getUploads());
    return filePath.replace(getUploads(), UploadsPrefix.UPLOADS);
  }

  /**
   * 上传头像
   *
   * @param avatar 头像文件
   * @return 文件路径
   */
  public static String uploadAvatar(MultipartFile avatar) {
    String avatarPath = upload(avatar, getAvatar());
    return avatarPath.replace(getAvatar(), UploadsPrefix.AVATAR);
  }

  /**
   * 上传文件
   *
   * @param file 文件
   * @return 文件路径
   */
  private static String upload(MultipartFile file, String path) {
    String fileName = file.getOriginalFilename();
    if (Objects.isNull(fileName)) {
      fileName = "unknown-file-name";
    }
    if (fileName.length() > MAX_FILE_NAME_LENGTH) {
      throw new BusinessException("上传失败, 文件名最大长度为: " + MAX_FILE_NAME_LENGTH);
    }
    if (file.getSize() > MAX_FILE_SIZE) {
      throw new BusinessException("上传失败, 文件最大大小为: " + ByteUtils.getSize(MAX_FILE_SIZE));
    }
    fileName = System.currentTimeMillis() + "_" + fileName;
    File destDir = new File(path);
    if (!destDir.exists()) {
      destDir.mkdirs();
    }
    String filePath = destDir + File.separator + fileName;
    File dest = new File(filePath);
    try {
      file.transferTo(dest);
    } catch (IllegalStateException | IOException e) {
      e.printStackTrace();
    }
    return filePath;
  }

}
