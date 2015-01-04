package com.fast.core.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;

public class CaptchaRender extends Render {

	private static final long serialVersionUID = 5742248334206158128L;
	private static final int WIDTH = 85, HEIGHT = 20;
	private static final String[] strArr = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y" };
	private String token = "";

	public CaptchaRender(String token) {
		this.token = token;
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		drawGraphic(image);
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		ServletOutputStream sos = null;
		try {
			sos = response.getOutputStream();
			ImageIO.write(image, "jpeg", sos);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sos != null)
				try {
					sos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}

	public static String GeneratoToken() {
		Random random = new Random();
		String sRand = "";
		for (int i = 0; i < 6; i++) {
			String rand = String.valueOf(strArr[random.nextInt(strArr.length)]);
			sRand += rand;
		}
		return sRand;
	}

	private void drawGraphic(BufferedImage image) {
		Graphics g = image.createGraphics();
		Random random = new Random();
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(getRandColor(200, 250));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(WIDTH);
			int y = random.nextInt(HEIGHT);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		g.setColor(getRandColor(160, 200));
		g.drawString(token, 20, 16);
		g.dispose();
	}

	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
