package forms;

import javax.sound.sampled.Clip;

public class SoundForm {

	Clip clip;
	
	String message;

	public void initialize() {
		setClip(null);
		setMessage(null);
	}
	
	public Clip getClip() {
		return clip;
	}

	public void setClip(Clip clip) {
		this.clip = clip;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
