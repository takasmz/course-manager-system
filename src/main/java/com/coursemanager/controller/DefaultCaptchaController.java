package com.coursemanager.controller;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.bingoohuang.patchca.color.ColorFactory;
import com.github.bingoohuang.patchca.color.SingleColorFactory;
import com.github.bingoohuang.patchca.custom.ConfigurableCaptchaService;
import com.github.bingoohuang.patchca.filter.predefined.CurvesRippleFilterFactory;
import com.github.bingoohuang.patchca.font.RandomFontFactory;
import com.github.bingoohuang.patchca.service.Captcha;
import com.github.bingoohuang.patchca.service.CaptchaService;
import com.github.bingoohuang.patchca.word.RandomWordFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Encoder;

@Controller
public class DefaultCaptchaController{
	
  private static final String DEFAULT_SESSION_KEY = "default-framework-key";
  private static String sessionKey = "default-framework-key";
  private static final String DEFAULT_CHARACTERS = "123456789abcdefghijklmnopqrstuvwxyz";
  private static int DEFAULT_FONT_SIZE = 25;
  private static int DEFAULT_WORD_LENGTH = 4;
  private static int DEFAULT_WIDTH = 80;
  private static int DEFAULT_HEIGHT = 35;




  public static String getSessionKey()
  {
    return sessionKey;
  }
  
  @RequestMapping("/captcha")
  @ResponseBody
  public String service(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0L);
    CaptchaService cs = create();
    HttpSession session = request.getSession();
	  InputStream in = null;
    Captcha captcha = cs.getCaptcha();
    File file = new File("d:\\temp\\captcha\\captcha.png");
    if(file.exists()){
    	file.delete();
	}else{
    	file.mkdirs();
	}
	response.setHeader("Access-Control-Expose-Headers" , "captcha");
    response.setHeader("content-type", "image/png");
	  response.setHeader("captcha",captcha.getChallenge());
    ImageIO.write(captcha.getImage(), "png", file);
    try {
		in = new FileInputStream(file);
		byte[] data = new byte[in.available()];
		in.read(data);
		BASE64Encoder encoder = new BASE64Encoder();
		String base64 = encoder.encode(data);
		session.setAttribute(sessionKey, captcha.getChallenge());
		return base64;
	}catch (Exception e){
    	e.printStackTrace();
    	return "";
	}finally {
    	in.close();
	}



  }
  
  
  public static CaptchaService create(int fontSize, int wordLength, String characters, int width, int height) {
	  ConfigurableCaptchaService service = null;
	  // 字体大小设置
	  RandomFontFactory ff = new RandomFontFactory();
	  ff.setMinSize(fontSize);
	  ff.setMaxSize(fontSize);
	  // 生成的单词设置
	  RandomWordFactory rwf = new RandomWordFactory();
	  rwf.setCharacters(characters);
	  rwf.setMinLength(wordLength);
	  rwf.setMaxLength(wordLength);
	  // 干扰线波纹
	  CurvesRippleFilterFactory crff = new CurvesRippleFilterFactory();
	  // 处理器
	  service = new ExConfigurableCaptchaService();
	  service.setFontFactory(ff);
	  service.setWordFactory(rwf);
	  service.setFilterFactory(crff);
	  // 生成图片大小（像素）
	  service.setWidth(width);
	  service.setHeight(height);
	  return service;
  }
  public static CaptchaService create() {
	  return create(DEFAULT_FONT_SIZE, DEFAULT_WORD_LENGTH, DEFAULT_CHARACTERS, DEFAULT_WIDTH, DEFAULT_HEIGHT);
  }
  
  
  	//随机变色处理
	static class ExConfigurableCaptchaService extends ConfigurableCaptchaService {
		
		private static final Random RANDOM = new Random();
		private List<SingleColorFactory> colorList = new ArrayList<SingleColorFactory>(); // 为了性能
		
		public ExConfigurableCaptchaService() {
			colorList.add(new SingleColorFactory(Color.blue));
			colorList.add(new SingleColorFactory(Color.black));
			colorList.add(new SingleColorFactory(Color.red));
			colorList.add(new SingleColorFactory(Color.pink));
			colorList.add(new SingleColorFactory(Color.orange));
			colorList.add(new SingleColorFactory(Color.green));
			colorList.add(new SingleColorFactory(Color.magenta));
			colorList.add(new SingleColorFactory(Color.cyan));
		}
		public ColorFactory nextColorFactory() {
			int index = RANDOM.nextInt(colorList.size());
			return colorList.get(index);
		}
		@Override
		public Captcha getCaptcha() {
			ColorFactory cf = nextColorFactory();
			CurvesRippleFilterFactory crff = (CurvesRippleFilterFactory) this.getFilterFactory();
			crff.setColorFactory(cf);
			this.setColorFactory(cf);
			return super.getCaptcha();
		}
	}

}
