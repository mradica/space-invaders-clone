import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Saucer{
  static BufferedImage saucerImage;
  static int saucerX = 1000;
  
  void importPicture(){ //imports the saucer's image, it is in a seperate routine as the drawSacucer method, so the the saucer doesn't lag when repainting
    try{
      saucerImage = ImageIO.read(new File("./img/saucer.png"));
    }
    catch(Exception e){
      System.out.print("image not read");
    }
  }
  
  void drawSaucer(Graphics g){ //draw's the image of the saucer
    g.drawImage(saucerImage, saucerX, 28 , null);
  }
  
  void moveSaucer(){ //if a saucer is on screen, this routine is called to move the ship across the sreen every time the program repaints
    if(saucerX == -100)
      saucerX =1000;
    saucerX = saucerX - 4;
  }
  
  boolean isOffFrame(){ //returns true when the saucer is no longer on the frame, so that movement can be stoped, and the saucer can be reset
    if(saucerX == -100)
    return true;
    else return false;
  }
  
  Rectangle getBounds(){ // gets the bounds of the image, used to detect collisoins
    Rectangle r = new Rectangle (saucerX,28,saucerImage.getWidth(),saucerImage.getHeight()); // returns a rectangle of dimensions
    return r;
  }
  
  int getX(){ //gets the x positoin of the saucer
    return saucerX;
  }
  
  void reset(){ //brings the saucer back to its origoinal position, so that it can move across screen again at a random time
   saucerX = 1000; //1000 pixels is off frame
  }
  
  
}