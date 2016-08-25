package com.boxamazing.webfront.util;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
/**
 * <p>
 * 生成验证用的图片
 * </p>
 * <p><br /></p> User: Abu Date: 2009-7-2 Time: 12:04:15
 */
public class ImageGenerator {
	// 随机生成的字符串的长度
	private final int stringLength = 4;
	// 图片的背景色
	private final Color bgColor = new Color(240, 240, 150);
	// 干扰线的宽度
	private final int lineWidth = 2;
	// 干扰线的数量
	private final int lineNumber = 200;
	// 字体大小
	private final int fontSize = 22;
	// 随机字符串的字体
	private final Font font = new Font("Arial", Font.BOLD, fontSize);
	// 随机生成的字符串
	private String randString;
	// 图片宽度
	private int width;
	// 图片高度
	private int height;
	/**
	 * <p>
	 * 缺省构造函数
	 * </p>
	 */
	public ImageGenerator() {
		this(97,30);
	}
	/**
	 * <p>
	 * 自定义构造函数
	 * </p>
	 * 
	 * @param width :
	 *            图片宽度
	 * @param height :
	 *            图片高度
	 */
	public ImageGenerator(int width, int height) {
		this.width = width;
		this.height = height;
	}
	/**
	 * <p>
	 * 产生随机前景色,这里的色调基准为深色调
	 * </p>
	 * 
	 * @return 随机颜色
	 */
	private Color createRandColor() {
		Random random = new Random();
		// 红色
		int red = random.nextInt(100);
		// 绿色
		int green = random.nextInt(100);
		// 蓝色
		int blue = random.nextInt(100);
		return new Color(red, green, blue);
	}
	/**
	 * <p>
	 * 根据预定义的字符长度,生成随机字符串,规则是字符为数字与大写字母的混合
	 * </p>
	 * 
	 * @return 生成的随机字符串
	 */
	private void createRandString() {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		// 如果随机数为1,生成数字,否则生成大写字母
		for (int i = 0; i < this.stringLength; i++) {
			if (random.nextInt(2) == 1) {
				sb.append((char) (random.nextInt(10) + 48));
			} else {
				if(random.nextInt(3)==1){
					sb.append((char) (random.nextInt(26) + 65));
				}else{
					sb.append((char) (random.nextInt(26) + 97));
				}
			}
		}
		this.randString = sb.toString();
	}
	/**
	 * <p>
	 * 获得随机字符串
	 * </p>
	 * 
	 * @return 随机字符串
	 */
	public String getRandString() {
		return randString;
	}
	public BufferedImage createImage() {
		Random random = new Random();
		// 图片为指定宽度和高度的RGB类型图片
		BufferedImage image = new BufferedImage(this.width, this.height,
				BufferedImage.TYPE_INT_BGR);
		Graphics2D graphics = image.createGraphics();
		// 设置矩形颜色
		graphics.setColor(this.bgColor);
		// 绘制矩形
		graphics.fillRect(0, 0, this.width, this.height);
		// 设置边框颜色
		graphics.setColor(Color.GREEN);
		// 绘制边框
		graphics.drawRect(0, 0, this.width - 1, this.height - 1);
		// 绘制干扰线
		for (int i = 0; i < this.lineNumber; i++) {
			// 设置线条的颜色
			graphics.setColor(this.createRandColor());
			int x = random.nextInt(width - lineWidth - 5) + 1; 
			int y = random.nextInt(height - lineWidth - 5) + 1;
			int xl = random.nextInt(lineWidth);
			int yl = random.nextInt(lineWidth);
			graphics.drawLine(x, y, x + xl, y + yl);
		}
		// 产生随机字符串
		this.createRandString();
		graphics.setFont(this.font);
		
		// 将字符数组转化成字符数组
		char[] chars = this.getRandString().toCharArray();		
		
		for (int i=0; i<chars.length; i++) {						
			graphics.setColor(this.createRandColor());
			
			String letter = new Character(chars[i]).toString();
			
			// 这里调整字符的间距和高度,水平偏差为10,高度偏差为15
			graphics.drawString(letter, (random.nextInt(10) + (this.fontSize-10)*(i+1) +3), (random.nextInt(10) + 23));
		}			
		// 图片生效
		graphics.dispose();
		return image;
	}
}
