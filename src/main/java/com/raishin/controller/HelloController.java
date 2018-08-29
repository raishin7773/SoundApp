package com.raishin.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import forms.SoundForm;

@Controller
@EnableAutoConfiguration
@SessionAttributes(types = { SoundForm.class})
public class HelloController {

	@Autowired
    ResourceLoader resourceLoader;
	
	@RequestMapping("/")
	public String hello(@ModelAttribute SoundForm soundForm) {
		soundForm.initialize();
		return "hello";
	}

	@ModelAttribute
	public SoundForm getForm(HttpServletRequest request) {
		return new SoundForm();
	}
	
	@RequestMapping(value = "/sound",params ="start")
	public String soundClip(@ModelAttribute SoundForm soundForm) throws InterruptedException, Exception {

		Clip clip = soundForm.getClip();
		if(clip != null) {
			clip.stop();
			clip.flush();
			clip.close();
		}
		Resource resource = resourceLoader.getResource("classpath:sound/near_a_brook.wav");
		clip = createClip(resource.getFile());
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		soundForm.setClip(clip);
		return "hello";
	}

	@RequestMapping(value = "/sound",params ="stop")
	public String stopClip(@ModelAttribute SoundForm soundForm) throws InterruptedException, Exception {

		Clip clip = soundForm.getClip();
		if(clip != null) {
			clip.stop();
			clip.flush();
			clip.close();
		}
		return "hello";
	}
	
	public static Clip createClip(File path) {
		// 指定されたURLのオーディオ入力ストリームを取得
		try (AudioInputStream ais = AudioSystem.getAudioInputStream(path)) {

			// ファイルの形式取得
			AudioFormat af = ais.getFormat();

			// 単一のオーディオ形式を含む指定した情報からデータラインの情報オブジェクトを構築
			DataLine.Info dataLine = new DataLine.Info(Clip.class, af);

			// 指定された Line.Info オブジェクトの記述に一致するラインを取得
			Clip c = (Clip) AudioSystem.getLine(dataLine);

			// 再生準備完了
			c.open(ais);

			return c;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}
}
