package com.bg.commons.utils;

import java.util.UUID;

public class UUIDUtil {

  public static String getUuid() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

}
