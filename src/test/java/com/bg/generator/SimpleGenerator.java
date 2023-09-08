package com.bg.generator;


import com.bg.generator.config.SimpleGeneratorConfig;
import com.bg.generator.handler.SimpleGeneratorHandler;

/**
 * @author jiewus
 * @date 2022/7/2
 **/
public class SimpleGenerator {

  public static void main(String[] args) throws Exception {
    SimpleGeneratorConfig config = new SimpleGeneratorConfig();
    config.setModuleName("user");
    config.setName("UserSearch");
    config.setDesc("用户描述");
    config.setAuthor("wujie");
    config.setGenerateController(true);
    config.setGenerateService(true);
    config.setGenerateMapper(true);
    SimpleGeneratorHandler.generateSimple(config);
  }

}
