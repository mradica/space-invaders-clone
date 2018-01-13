import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList.*;


public class HighScore{
  
  String getScore(){ //This method reads the highscore.txt file, gets the text on the file, and returns the contents of the file
    File file = new File("highscore.txt");
    String highscore = "";
    try{
      Scanner fileScanner = new Scanner(file);
      while (fileScanner.hasNextLine()) {
        highscore = fileScanner.nextLine();
      }
    }
    catch(java.io.FileNotFoundException e){
    }
    return highscore;
  }
  
  int getScoreInt(){ //This method reads the highscore.txt file, gets the text on the file, converts the contents of the file to an integer, and retruns the int
                    //This can be used so that the old highscore can be compared to the player's current score
    File file = new File("highscore.txt");
    String highscore = "";
    try{
      Scanner fileScanner = new Scanner(file);
      while (fileScanner.hasNextLine()) {
        highscore = fileScanner.nextLine(); //scans the text on the text file where the highscore is saved
      }
    }
    catch(java.io.FileNotFoundException e){
    }
    int scoreNumb = Integer.parseInt(highscore); //converts the string to an integer
    return scoreNumb;
  }
  
  void setScore(int i){ //sets the value of a new highscore
    String newScore = String.valueOf(i); //get the user's score and converts it to a string
    try{
      PrintWriter textFile = new PrintWriter("highscore.txt"); //overwrites the old file
      textFile.println(newScore); //prints the new highscore in the text file
      textFile.close();
    }
    catch(java.io.FileNotFoundException r){
    }
    
  }
  
}