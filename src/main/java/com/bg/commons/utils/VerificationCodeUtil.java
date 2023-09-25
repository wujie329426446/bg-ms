package com.bg.commons.utils;

import com.bg.commons.constant.CachePrefix;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class VerificationCodeUtil {

  /**
   * 验证码图片的长
   **/
  public static int WEIGHT = 110;
  /**
   * 验证码图片的高
   */
  public static int HEIGHT = 38;
  /**
   * 用来保存验证码的文本内容
   **/
  public static String TEXT;
  /**
   * 获取随机数对象
   **/
  public static Random R = new Random();
  /**
   * 字体数组
   **/
  public static String[] FONT_NAMES = {"宋体", "华文楷体", "黑体", "微软雅黑", "楷体_GB2312"};
  /**
   * 验证码数组
   **/
  public static String CODES = "23456789acdefghjkmnopqrstuvwxyzACDEFGHJKMNPQRSTUVWXYZ";
  /**
   * 生成的验证码的个数
   **/
  public static int CODE_NUM = 4;
  /**
   * 255
   **/
  public static int TWO_FIVE_FIVE = 255;

  /**
   * 获取随机的颜色
   */
  public static Color randomColor() {
    //这里为什么是150，因为当r，g，b都为255时，即为白色，为了好辨认，需要颜色深一点。
    int r = R.nextInt(150);
    int g = R.nextInt(150);
    int b = R.nextInt(150);
    //返回一个随机颜色
    return new Color(r, g, b);
  }

  /**
   * 获取随机字体
   */
  public static Font randomFont() {
    //获取随机的字体
    int index = R.nextInt(FONT_NAMES.length);
    String fontName = FONT_NAMES[index];
    //随机获取字体的样式，0是无样式，1是加粗，2是斜体，3是加粗加斜体
    int style = R.nextInt(4);
    //随机获取字体的大小
    int size = R.nextInt(5) + 24;
    //返回一个随机的字体
    return new Font(fontName, style, size);
  }

  /**
   * 获取随机字符
   */
  public static char randomChar() {
    int index = R.nextInt(CODES.length());
    return CODES.charAt(index);
  }

  /**
   * 画干扰线，验证码干扰线用来防止计算机解析图片
   */
  public static void drawLine(BufferedImage image) {
    int num = 155;
    //定义干扰线的数量
    Graphics2D g = (Graphics2D) image.getGraphics();
    for (int i = 0; i < num; i++) {
      int x = R.nextInt(WEIGHT);
      int y = R.nextInt(HEIGHT);
      int xl = R.nextInt(WEIGHT);
      int yl = R.nextInt(HEIGHT);
      g.setColor(VerificationCodeUtil.getRandColor(160, 200));
      g.drawLine(x, y, x + xl, y + yl);
    }
  }

  /**
   * 创建图片的方法
   */
  public static BufferedImage createImage() {
    //创建图片缓冲区
    BufferedImage image = new BufferedImage(WEIGHT, HEIGHT, BufferedImage.TYPE_INT_RGB);
    //获取画笔
    Graphics2D g = (Graphics2D) image.getGraphics();
    // 设定图像背景色(因为是做背景，所以偏淡)
    g.setColor(VerificationCodeUtil.getRandColor(200, 250));
    g.fillRect(0, 0, WEIGHT, HEIGHT);
    //返回一个图片
    return image;
  }

  /**
   * 获取验证码图片的方法
   */
  public static BufferedImage getImage() {
    BufferedImage image = createImage();
    //获取画笔
    Graphics2D g = (Graphics2D) image.getGraphics();
    StringBuilder sb = new StringBuilder();
    drawLine(image);
    //画四个字符即可
    for (int i = 0; i < CODE_NUM; i++) {
      //随机生成字符，因为只有画字符串的方法，没有画字符的方法，所以需要将字符变成字符串再画
      String s = randomChar() + "";
      //添加到StringBuilder里面
      sb.append(s);
      //定义字符的x坐标
      float x = i * 1.0F * WEIGHT / 4;
      //设置字体，随机
      g.setFont(randomFont());
      //设置颜色，随机
      g.setColor(randomColor());
      g.drawString(s, x, HEIGHT - 5);
    }
    TEXT = sb.toString();
    return image;
  }

  /**
   * 给定范围获得随机颜色
   */
  public static Color getRandColor(int fc, int bc) {
    Random random = new Random();
    if (fc > TWO_FIVE_FIVE) {
      fc = TWO_FIVE_FIVE;
    }

    if (bc > TWO_FIVE_FIVE) {
      bc = TWO_FIVE_FIVE;
    }

    int r = fc + random.nextInt(bc - fc);
    int g = fc + random.nextInt(bc - fc);
    int b = fc + random.nextInt(bc - fc);
    return new Color(r, g, b);
  }

  /**
   * 获取验证码文本的方法
   */
  public static String getText() {
    return TEXT;
  }

  public static String getEmailCode(String email) {
    return CachePrefix.EMAIL_CODE + email;
  }

  public static String getPhoneCode(String phone) {
    return CachePrefix.PHONE_CODE + phone;
  }

}
