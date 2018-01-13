import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Barricade{
  
  static BufferedImage bImage;
  
  void getImage(){ //gets the image used for the barricade
  try{
      bImage = ImageIO.read(new File("barricade.png"));
    }
    catch(Exception e){}
  }
  
  void drawBarricade(Graphics g, int i){ // draws the barricade at the spot it is sent
    g.drawImage(bImage,i ,  420, null);
  }
  
  Rectangle getBounds(int i){ // gets the bounds of the image, used to detect collisoins
    Rectangle r = new Rectangle (i,420,bImage.getWidth(),bImage.getHeight()); // returns a rectangle of dimensions
    return r;
  }
  
}