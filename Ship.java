import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Ship{
  
  static BufferedImage image;
  static int shipX = 170;
  private int velX = 0;
  static BufferedImage shipImage;
  
  void drawExp(Graphics g){ //draws the exploded ship image, called when the ship is out of lives
    try{
      image = ImageIO.read(new File("Untitled-1.png"));
    }
    catch(Exception e){}
    g.drawImage(image, shipX, 500, null);
    
  }
  
  void drawShip(Graphics g){ //drwas the ship image
    g.drawImage(shipImage, shipX, 500, null);
  }
  
  void drawHealthShip(Graphics g, int x, int y){ //draws the ship image, but with smaller dimensions to be shown in the top bar for health
    g.drawImage(shipImage, x , y,45,28, null); 
  }
  
  void getShipImage(){ // routine that imports the image used to draw the ship
  try{
      shipImage = ImageIO.read(new File("Ship.png"));
    }
    catch(Exception e){}
  }
  
  void move(){ //moves the ship according to key events
    if(shipX <= 0) //prevents the ship from going off frame left
      shipX = 0;
    
    if(shipX >= 930) //prevents the ship from going off frame right
      shipX = 930;
    
  shipX = shipX + velX; //changes the ships location, based on the velocity it is given
  }
  
  void setVelX(int velX){ //changes the ships velocity(how fast the x value changes right (+) or left (-))
    this.velX = velX;
  }
  
  int getPos(){ //returns the current position of the ship
   return shipX; 
  }
  
  Rectangle getBounds(){ // gets the bounds of the image, used to detect collisoins
    Rectangle r = new Rectangle (shipX,500,shipImage.getWidth(),shipImage.getHeight()); // returns a rectangle of dimensions
    return r;
  }
  
  void reset(){ //resets the ship back to it's original starting position, used when a new game is started
    shipX = 170;
    velX = 0; //makes sure it is no longer moving right or left
    
  }
  
}