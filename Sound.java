import java.io.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound{
  static Clip ufoClip;
  static Clip musicClip;
  
  void playShot(){ //uses clips to play the sound used when the ship is shooting, called when space is pressed
    
    File sound = new File("shoot.wav");
    
    try{
      Clip clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(sound));
      clip.start();
    }
    catch(Exception e){}
  }
  
  void playInvaderHit(){ //used clips to play the sound used when an invader is hit
    File sound = new File("invaderkilled.wav");
    try{
      Clip clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(sound));
      clip.start();
    }
    catch(Exception e){}
  }
  
  void playShipHit(){ //used clips to play the explosion sound when a ship is hit
    File sound = new File("explosion.wav");
    try{
      Clip clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(sound));
      clip.start();
    }
    catch(Exception e){}
  }
  
  void playSoundTrack(){ // plays the song used in the background during the game/menu, loops the song continuosly, unless the clip is called to stop
    File sound = new File("01 Space Invaders Extreme - Menu Song.wav");
    try{
      musicClip = AudioSystem.getClip();
      musicClip.open(AudioSystem.getAudioInputStream(sound));
      musicClip.start();
      musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    catch(Exception e){
    
    }
  }
  
  void stopSoundTrack(){ //stops the background music when this method is called
    try{
      musicClip.stop();
    }
    catch(Exception e){
    
    }
  }
  
  void playUFO(){ //plays the sound used when the ufo is flying, loops the clips ennough times for the ufo to exit the frame
    File sound = new File("ufo_highpitch.wav");
    try{
      ufoClip = AudioSystem.getClip();
      ufoClip.open(AudioSystem.getAudioInputStream(sound));
      ufoClip.start();
      ufoClip.loop(29);
    }
    catch(Exception e){
    
    }
  }
  
  void stopUFO(){ //stops playing the ufo sound, called when the UFO is hit with a bullet
    try{
      ufoClip.stop();
    }
    catch(Exception e){
    
    }
  }
  
  void playWhatIsLove(){ //plays the bonus sound track played when w is pressed
    File sound = new File("what is love 8 bit.wav");
    try{
      Clip clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(sound));
      clip.start();
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    catch(Exception e){
    
    }
    
  }
  
}