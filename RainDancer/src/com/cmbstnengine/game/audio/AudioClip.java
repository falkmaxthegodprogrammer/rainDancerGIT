package com.cmbstnengine.game.audio;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class AudioClip {
	
	private File file;
	
	public AudioClip(String path) {
	
		file = new File(path);
		
			if(!file.exists()) {
				System.out.println("Can't find audio clip!");
			}
		}
		
		public AudioInputStream getAudioStream() {
			try {
				
				AudioInputStream ais = AudioSystem.getAudioInputStream(file);
				
				AudioFormat baseFormat = ais.getFormat();
				
				AudioFormat decodeFormat = new AudioFormat(
						AudioFormat.Encoding.PCM_SIGNED,
						baseFormat.getSampleRate(),
						16,
						baseFormat.getChannels(),
						baseFormat.getChannels() * 2,
						baseFormat.getSampleRate(),
						false
					);

				AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);

				return dais;
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
}


