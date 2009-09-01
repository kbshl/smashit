package manager;

import java.net.URL;
import java.util.HashMap;
import java.applet.Applet;
import java.applet.AudioClip;
import game.Finals;

public class SoundManager {
	private static SoundManager sm = null;
	private HashMap sounds;
	
	private SoundManager() {
		sounds = new HashMap();
	}
	
	public static SoundManager getSoundManager(){
		if(sm == null)
			sm = new SoundManager();
		return sm;
	}
	
	private Object loadResource(String name){
		URL url=null;
		url = getClass().getClassLoader().getResource(name);
		return loadResource(url);
	}

	private Object getResource(String name){
		Object res = sounds.get(name);
		
		if(res == null){
			res = loadResource(Finals.SOUND_PATH + name);
			sounds.put(name,res);
		}
		
		return res;
	}

	private Object loadResource(URL url){
		return Applet.newAudioClip(url);
	}
	
	private AudioClip getAudioClip(String name){
		return (AudioClip)getResource(name);  
	}

	public void playSound(final String name){
		new Thread(
				new Runnable() {
					public void run() {
						getAudioClip(name).play();
					}
				}
		).start();
	}
		
	public void loopSound(final String name){
		new Thread(
				new Runnable() {
					public void run() {
						getAudioClip(name).loop();
					}
				}
		).start();
	}
	
	public void stopSound(final String name){
		new Thread(
				new Runnable() {
					public void run() {
						getAudioClip(name).stop();
					}
				}
		).start();
	}
}
