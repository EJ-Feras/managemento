package service;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Toene abspielen .
 * 
 * @author Feras
 *
 */
public class Sound {

	public Sound() {
	}

	public void playSound(String url) {
		try {
			Media someSound = new Media(getClass().getResource(url).toString());
			playMedia(someSound);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private void playMedia(Media m) {
		if (m != null) {
			MediaPlayer mp = new MediaPlayer(m);
			mp.play();
		}
	}

}
