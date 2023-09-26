package com.bg.commons.utils;

import com.bg.commons.constant.CachePrefix;

public class VerifyCodeUtil {

  public static String getUsernamePasswordCode(String username) {
    return CachePrefix.USERNAME_CODE + username;
  }

  public static String getEmailCode(String email) {
    return CachePrefix.EMAIL_CODE + email;
  }

  public static String getPhoneCode(String phone) {
    return CachePrefix.PHONE_CODE + phone;
  }

}
