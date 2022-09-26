package main;//https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/Clip.html

import javax.sound.sampled.Clip;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class Sound{
    Clip clip;
    URL soundURL[] = new URL[30];
    
    public Sound(){
        soundURL[0] = getClass().getResource("/res/sound/mainTheme.wav");
        soundURL[1] = getClass().getResource("/res/sound/coin.wav");
        soundURL[2] = getClass().getResource("/res/sound/hurt.wav");
        soundURL[3] = getClass().getResource("/res/sound/powerup.wav");
        soundURL[4] = getClass().getResource("/res/sound/select.wav");
        soundURL[5] = getClass().getResource("/res/sound/finished.wav");
        soundURL[6] = getClass().getResource("/res/sound/cantinaband.wav");
    }
    
    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            
        }catch(Exception e){
        
        }
    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}