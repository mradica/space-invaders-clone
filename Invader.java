import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Invader{
  
  static int fleetX = 1;
  static int fleetY = 30;
  static boolean rightDirection = true;
  static Timer timer;
  static BufferedImage spaceInvaderGuy;
  static BufferedImage image1 = null;
  static Image image2 = null;
  static Image image3 = null;
  
  void drawPicture2(Graphics g, int x, int y){
    g.drawImage(image1, fleetX + x, fleetY + y, null); //draws the image of one of the invaders shown in the fleet
  }
  
  void drawPicture3(Graphics g, int x, int y){
    g.drawImage(image2, fleetX + x, fleetY + y, null); //draws the image of one of the invaders shown in the fleet
  }
  
  void drawPicture4(Graphics g, int x, int y){
     g.drawImage(image3, fleetX + x, fleetY + y, null); //draws the image of one of the invaders shown in the fleet
  }
  
  void loadPictures(){ //loads up all of the images used to draw the aliens pictures. This is in a seperate routine from the method used
                       //to draw the invaders, so that the pictures are re loaded every time the program repaints
    try{
      image1 = ImageIO.read(new File("Untitled-7.png"));
    }
    catch(Exception e){
      System.out.print("image not read");
    }
    
    try{
      image2 = ImageIO.read(new File("Untitled-8.png"));
    }
    catch(Exception e){
      System.out.print("image not read");
    }
    
    try{
      image3 = ImageIO.read(new File("Untitled-9.png"));
    }
    catch(Exception e){
      System.out.print("image not read");
    }
    
  }
  
  
  void moveFleet(){ //finds the direction the fleet needs to move in and ajdusts the y and x coordinates accordinly
    if(fleetX == 1 && fleetY == 30)
      rightDirection = true;
    
    if (fleetX >= 430){
      rightDirection = false;
      fleetY = fleetY +50;
    }
    
    else if(fleetX <= 0){
      rightDirection = true;
      fleetY = fleetY + 50;
    }
    
    if(rightDirection == true)
      fleetX = fleetX + 1;
    
    if(rightDirection == false)
      fleetX = fleetX - 1;
    
  }
  
  Rectangle getBounds(int x, int y){ // gets the bounds of the image, used to detect collisoins
    Rectangle r = new Rectangle (fleetX + x,fleetY+ y,image1.getWidth(),image1.getHeight()); // returns a rectangle of dimensions
    return r;
  }
  
  void drawExplosion(Graphics g){ //draws a new picture, whih is the explosion image for the invaders.
                                  //this method is called when the bullet intersects the invader's bounds
    try{
      spaceInvaderGuy = ImageIO.read(new File("1206570436270960825johnny_automatic_battle.svg.med.png"));
    }
    catch(Exception e){
      System.out.print("image not read");
    }
    g.drawImage(spaceInvaderGuy, fleetX, fleetY, null);
  }
  
  boolean endGame(int y){ //this method determines if the invaders are at the bottom of the screen. If they are, true will be returned
    if(fleetY + y >=490){
      return true; //when true is retrun the game will end
    }
    
    return false; //if game doesn't end yet
  }
  
  int getFleetX(){ //gets the x position of the invaders
    return fleetX;
  }
  
  int getFleetY(){ //gets the y position of the invaders
    return fleetY;
  }
  
  void reset(){ //resets the invaders back to thier original positoin when the game starts. Used when the game is restarted or before entering a new level 
    fleetX = 1;
    fleetY = 30; 
    
  } 
  
}