package com.cmbstnengine.game.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import com.cmbstnengine.game.GamePanel;

public class AudioPlayer {
	
	public static synchronized void playSound(AudioClip sfx, double vol) {
		Thread thread = new Thread() {
			public void run() {
				try {
				
					AudioInputStream stream = sfx.getAudioStream();
				
						Clip clip = AudioSystem.getClip();
						
						clip.open(stream);
						setVol(vol, clip);
						clip.start();
							
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}; thread.start();
	}
	
	public static synchronized void playSoundOnce(AudioClip sfx, double vol) {
		Thread thread2 = new Thread() {
			public void run() {
				try {
				
					AudioInputStream stream = sfx.getAudioStream();
				
						Clip clip = AudioSystem.getClip();
						
						clip.open(stream);
						setVol(vol, clip);
						clip.start();
						clip.close();
							
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}; thread2.start();
	}
	
	
	private static void setVol(double vol, Clip clip) {
		if(vol > 1.0) return;
		FloatControl gain = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		float dB = (float) (Math.log(vol + GamePanel.masterVolume) / Math.log(10) * 20);
		gain.setValue(dB);
	}
	
}
