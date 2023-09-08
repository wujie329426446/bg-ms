package com.bg.system.controller;

import cn.hutool.core.io.FileUtil;
import com.bg.commons.api.ApiResult;
import com.bg.commons.log.annotation.OperationLog;
import com.bg.commons.log.enums.OperationLogType;
import com.bg.commons.utils.SecurityUtil;
import com.bg.commons.utils.UploadUtil;
import com.bg.config.properties.BgProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ProjectName: bg-ms
 * @Author: jiewus
 * @Description: 文件本地上传下载Controller
 * @Date: 2023/9/5 17:42
 */
@Slf4j
@RestController
@RequestMapping("/v1/api/admin/local/file")
@Tag(name = "本地文件管理")
public class LocalFileController {

  @Autowired
  private BgProperties bgProperties;

  /**
   * 上传单个文件
   *
   * @return
   */
  @PostMapping("/delete")
  @OperationLog(name = "删除单个文件", type = OperationLogType.DELETE)
  @Operation(summary = "删除单个文件")
  public ApiResult<Boolean> upload(@RequestParam("file") String filePath,
      @RequestParam("type") String type) throws Exception {
    String fileDeletePath = bgProperties.getUploadFolder() + SecurityUtil.getUser().getUserId() + '/' + StringUtils.substringAfterLast(filePath, "/");
    log.info("fileDeletePath:{}", fileDeletePath);
    return ApiResult.success(FileUtil.del(fileDeletePath));
  }

  /**
   * 上传单个文件
   *
   * @return
   */
  @PostMapping("/upload")
  @OperationLog(name = "上传单个文件", type = OperationLogType.UPLOAD)
  @Operation(summary = "上传单个文件")
  public ApiResult<String> upload(@RequestParam("file") MultipartFile multipartFile,
      @RequestParam("type") String type) throws Exception {
    log.info("multipartFile = " + multipartFile);
    log.info("ContentType = " + multipartFile.getContentType());
    log.info("OriginalFilename = " + multipartFile.getOriginalFilename());
    log.info("Name = " + multipartFile.getName());
    log.info("Size = " + multipartFile.getSize());
    log.info("type = " + type);

    // 上传文件，返回保存的文件名称
    String saveFileName = UploadUtil.upload(bgProperties.getUploadFolder() + '/' + SecurityUtil.getUser().getUserId(), multipartFile, originalFilename -> {

      // 文件后缀
      String fileExtension = FilenameUtils.getExtension(originalFilename);

      // 这里可自定义文件名称，比如按照业务类型/文件格式/日期
      String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssS")) + RandomStringUtils.randomNumeric(6);
      String fileName = dateString + "." + fileExtension;
      return fileName;
    });

    // 上传成功之后，返回访问路径，请根据实际情况设置
    String fileAccessPath = bgProperties.getResourceAccessUrl() + SecurityUtil.getUser().getUserId() + '/' + saveFileName;
    log.info("fileAccessPath:{}", fileAccessPath);

    return ApiResult.success(fileAccessPath);
  }

}
