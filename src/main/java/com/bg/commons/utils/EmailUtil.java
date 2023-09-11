package com.bg.commons.utils;

import java.util.Map;

/**
 * @ProjectName: bg-ms
 * @Author: jiewus
 * @Description: 邮件工具类
 * @Date: 2023/9/8 11:03
 */
public class EmailUtil {

  /**
   * 获取发送给管理员的邮件内容
   *
   * @param params 填充的参数
   * @return 邮件内容
   */
  static String getEmailTemplateAdminContent(Map<String, String> params) {
    String emptyString = "";
    String websiteName = params.getOrDefault("websiteName", emptyString);
    String name = params.getOrDefault("name", emptyString);
    String content = params.getOrDefault("content", emptyString);
    String website = params.getOrDefault("website", emptyString);
    String articleId = params.getOrDefault("articleId", emptyString);

    return "<!DOCTYPE html>" +
        "<html>" +
        "<head>" +
        "<meta charset=\"UTF-8\">" +
        "<title>" + websiteName + " Email</title>" +
        "</head>" +
        "<body>" +
        "<h3>" + websiteName + "有新的评论回复</h3>" +
        "<p>来自" + name + "的评论：" + content + "</p>" +
        "<br>" +
        "<a href=\"" + website + "article/" + articleId + "\">查看详情</a>" +
        "</body>" +
        "</html>";
  }

  static String getEmailTemplateUserContent(Map<String, String> params) {
    String emptyString = "";
    String websiteName = params.getOrDefault("websiteName", emptyString);
    String name = params.getOrDefault("name", emptyString);
    String content = params.getOrDefault("content", emptyString);
    String website = params.getOrDefault("website", emptyString);
    String articleId = params.getOrDefault("articleId", emptyString);

    return "<!DOCTYPE html>" +
        "<html>" +
        "<head>" +
        "    <meta charset=\"UTF-8\">" +
        "    <title>" + websiteName + " Email</title>" +
        "</head>" +
        "<body>" +
        "<h3>你在" + websiteName + "的评论有人回复了,快去查看吧!</h3>" +
        "<p>来自" + name + "的评论：" + content + "</p>" +
        "<br>" +
        "<a href=\"" + website + "article/" + articleId + "\">查看详情</a>" +
        "</body>" +
        "</html>";
  }

}
