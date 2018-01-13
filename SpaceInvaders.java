//Marcus Radica
//Grade 12 computer science CPT

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip; //imports all needed packages

public class SpaceInvaders{
  
  static Component source;
  static boolean bullet = false;
  static int bulletY = 490;
  static Graphics g;
  static Bullet shot;
  static int xShot;
  static boolean hasShot = false;
  static boolean hit[][] = new boolean[12][5]; //creates a 2d array of booleans to track wether each invader has been hit
  static boolean explosion = false;
  static Invader[][] invaders = new Invader[12][5]; //creates a 2d array of invader objects, which makes up the grid of invaders
  static int kdratio = 0;
  static InvaderShot snipe = new InvaderShot();
  static boolean invaderBullet = true;
  static boolean newShot = true;
  static int invaderShot = 47 * ((int)(Math.random()*7)+1); //random X value generated to decide which invader shoots when the game starts
  static int inShotY = 35 * ((int)(Math.random()*4)+1); //random Y value generated to decide which invader shoots when the game starts
  static Ship ship = new Ship(); //creates the ship object
  static boolean movement = true;
  static int health = 3;
  static int fleetSpeed = 2000;
  static Timer moveTimer;
  static int score = 0;
  static JFrame window = new JFrame("space invaders"); //creates the window all the components of the game are added to
  static HighScore highscore = new HighScore();
  static Sound sound = new Sound();
  static Saucer saucer = new Saucer();
  static boolean newSaucer = false;
  static SquareDisplay space = new SquareDisplay();
  static Barricade [] base = new Barricade[4];
  static int [] baseInt = new int[4]; //array of health integers assigned to each barricade object
  static boolean game = false;
  static boolean barricades = false;
  static boolean wPressed = false;
  
  private static class SquareDisplay extends JPanel {
    
    public void paintComponent(Graphics g){
      super.paintComponent(g);
      if(health == 0){ //finds out if the ship is out of lives
        ship.drawExp(g); //draws the exploded ship image
        fleetSpeed = 2000;
        game = false;
        sound.stopUFO();
        movement = false; //when movement is false, the key listeners no longer do anything, so the user can no longer control the ship
      }
      else //if the ship still has lives
      ship.drawShip(g); //draws the ship
      printHealth(g); //prints the health bar at the top
      
      if (health > 0){ 
      if(kdratio == 60){ //if all invaders have been cleared
        health ++; //gives the user 1 extra life
        fleetSpeed = fleetSpeed - 400; //speeds up the invaders when a new wave enters
         reset(); //resets everything on screen
         kdratio = 0; //resets the invader killed counter
      }
      
      if(newSaucer == true){
        if(saucer.isOffFrame()){ //if the saucer has already passed by the fram without being hit
          newSaucer = false; //allows a new saucer to be generated
        }
      saucer.drawSaucer(g); //draws the saucer
      saucer.moveSaucer(); //moves the saucer
      }
      
      for(int i = 0; i<5; i++){ //loops thruogh the entire grid of invader objects
        for(int j = 0; j<12; j++){
          if(hit[j][i]==false){ //if they have not been hit
            if(invaders[j][i].endGame(i * 35)){ //if the invaders have reacher the bottom of the frame, the game ends
              printLoseMessage(g);
              invaderBullet = false;
              game = false;
              health = 0;
            }
            else{ //if the game is still going, and the invadershave not been hit, print out the appropriate invader picture, according to what row they are in
              if(i == 4 || i ==3)
                invaders[j][i].drawPicture3(g,j * 47, i * 35);
              if(i == 2 || i == 1)
                invaders[j][i].drawPicture2(g,j * 47, i * 35);
              if( i == 0)
                invaders[j][i].drawPicture4(g,j * 47, i * 35);
            }
          }
        }
      }
      if(bullet == true){
        shot = new Bullet(); //creates a new bullet
        shot.draw(g); //draws the bullet
      }
      if (invaderBullet == true){
        snipe.draw(g); //draws the invader's bullet
        snipe.moveShot(); //move the bullet every time repaint is called
      }
      if(barricades){ // if the barricades are activated
        for(int h=0; h<4; h++){ //cycles 4 times to create 4 barricades
          if(baseInt[h] > 0){ //if the health is greater than zero, the barricade is stil created
          base[h].drawBarricade(g, (300 * h) + 5); //draws the baricade
          g.drawString(String.valueOf(baseInt[h]), (h *300) + 40 , 460); //prints the health the barricade has left
          }
        }
      }
      }
      
      else{ //if the user is out of health
        printLoseMessage(g);
        health = 0;
      }
      
    }
  }
  
  public static class KeyHandler implements KeyListener{
    boolean space = false; //booleans that are made true when thier respective keys are pressed
    boolean left = false; // using booleans makes it possible to
    boolean right = false; // recognize the user pressing multiple keys
    
    public void keyTyped(KeyEvent e){}
    
    public void keyPressed(KeyEvent e){
      source = (Component)e.getSource();
      
      if (e.getKeyCode() == KeyEvent.VK_W){
        if(wPressed == false){ //boolean makes sure the song played can't be played multiple times at once
          wPressed = true;
        sound.stopSoundTrack(); //stops the origoinal music
        sound.playWhatIsLove(); //plays new song
        }
      }
      if (e.getKeyCode() == KeyEvent.VK_B) //b is pressed the boolean barricades is made true, now now the barricades will be painted in the paint component
        barricades = true;
      
      if (e.getKeyCode() == KeyEvent.VK_LEFT){
        left = true;
      }
      
      if (e.getKeyCode() == KeyEvent.VK_RIGHT){
        right = true;
      }
      if (e.getKeyCode() == KeyEvent.VK_SPACE){
        if(game ==true) //makes sure the bullet sound isn't played when the game is over
        space = true;
      }
      if(e.getKeyCode() == KeyEvent.VK_ESCAPE){ //if the game is over, the user is able to press esc to return to the main menu
        if(health==0){
          mainMenu();
          window.setVisible(true);
          window.setEnabled(true); //refreshed the window to show the menu
        }
      }
      
      if(left == true){
        if(movement)
          ship.setVelX(-10); //sets the velocity so that the ships x position will contantly move 10 pixels to the left until the key is released
      }
      if( right == true){
        if(movement)
          ship.setVelX(10); //sets the velocity so that the ships x position will contantly move 10 pixels to the right until the key is released
          
      }
      if(space == true){ //if sapce is pressed, a new bullet is created, and the sound for a bullet is played
       if(movement){
          if(bullet == false){
            sound.playShot();
            bulletY = 490;
            bullet = true;
            xShot =  ship.getPos() +34; // the bullets x position is set to the middle of the ship image
          }
        } 
      }
    }
    
    public void keyReleased(KeyEvent e){
    if (e.getKeyCode() == KeyEvent.VK_LEFT){ //stops the velocity from being added to the ship, which stops movement left
      ship.setVelX(0);
        left = false;
      }
      
      if (e.getKeyCode() == KeyEvent.VK_RIGHT){ //stops the velocity from being added to the ship, which stops movement right
        ship.setVelX(0);
        right = false;
      }
      
      if (e.getKeyCode() == KeyEvent.VK_SPACE){
        space = false; //makes sure bullets are no longer fired
      }
    }
    
  }
  
  public static void main(String [] args){
    mainMenu(); //starts off the game on the main menu
    sound.playSoundTrack(); //plays the soundtrack as soon as the game starts
    movementTimers(); //starts the timers, which move objects on the screen
    window.setIconImage(new ImageIcon("./img/Space_invader.png").getImage());
    window.setSize(1000, 600);
    window.setLocation(0,0);  
    window.setVisible(true);
    window.setResizable(false);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  public static void mainMenu(){ //in the main menu three buttons are added to a panel, each button has an action listener
    JPanel content = new JPanel();
    content.setLayout(new GridLayout(4,1)); //creates the layout tat the buttons are added to
    content.setBackground(Color.black);
    Image image = null;
    try{
    image = ImageIO.read(new File("./img/space_invaders_second_row.PNG"));
    }
    catch(Exception e){}
    JLabel picLabel = new JLabel(new ImageIcon(image)); //makes a label,which contains the picture shown in the main menu
    JButton gameButton = new JButton("Play Space Invaders");
    gameButton.setFont(new Font("Impact",Font.BOLD, 72));
    gameButton.setOpaque(true);
    gameButton.setBackground(Color.YELLOW);
    gameButton.setBorder(null);
    JButton rulesButton = new JButton("How to play");
    rulesButton.setFont(new Font("Impact",Font.BOLD, 72));
    rulesButton.setOpaque(true);
    rulesButton.setBackground(Color.BLUE);
    rulesButton.setBorder(null);
    JButton exitButton = new JButton("Quit");
    exitButton.setFont(new Font("Impact",Font.BOLD, 72));
    exitButton.setOpaque(true);
    exitButton.setBackground(Color.YELLOW);
    exitButton.setBorder(null); //the looks of the buttons are set
    content.add(picLabel);
    content.add(gameButton);
    content.add(rulesButton);
    content.add(exitButton); //buttons are added to the content pane
    
    window.setContentPane(content);
    gameButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt) {
          if(health <= 0){ 
            health = 3;
            reset();
            ship.reset();
            movement = true;
            inShotY = 700;
          }
          game = true;
          playGame();
          reset();
        } //resets all variables, and then starts the space invaders game
    });
    exitButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt) {
          System.exit(0); //exits the game
        }
    });
    rulesButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt) {
          rules(); //shows the rules
        }
    });
  }
  

  public static void playGame(){ //when the play game option is selected on the main menu, te score is reset and the paint component is made the content pane
    score = 0;
    space.setBackground(Color.BLACK);
    window.setContentPane(space);
    space.setFocusable(true);
    space.requestFocusInWindow();
    
    window.setVisible(true);
    window.setEnabled(true); //refreshed the window
    KeyHandler keyListener = new KeyHandler();
    space.addKeyListener(keyListener); //adds the key listener
    
    ship.getShipImage(); //imports the ship's picture
    saucer.importPicture(); //imports the saucer's picture
    for(int h = 0 ; h<4 ; h++){ //loops through to create all 4 barrcades,import the image and give each barricade health
      base[h] = new Barricade();
      base[h].getImage();
      baseInt[h] = 5;
    }
  }
  
  public static void movementTimers(){ //timers needed for the games
    ActionListener action = new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        space.repaint();
        ship.move();
      }
    };
    Timer time = new Timer(10 , action); //repaints the frame, and sees if the ship has changed position every 10 miliseconds
    time.start();
    
    
    for(int i = 0; i<5; i++){
      for(int j = 0; j<12; j++){ //loops through to create all the invader objects, make the hit boolean false, and load the invader's pictures
        invaders[j][i] = new Invader();
        hit[j][i] = false;
        invaders[j][i].loadPictures();
      }
    }
    
    ActionListener action2 = new ActionListener() {
      
      public void actionPerformed(ActionEvent evt) {
        for(int i = 0; i<5; i++){
          for(int j = 0; j<12; j++){
            invaders[j][i]. moveFleet();
          }
        }
      }
    };
    moveTimer = new Timer(fleetSpeed, action2 ); //changes the position of the fleet every timer the timer goes off
                                                 //fleet speed changes after every wave, so that the aliens speed up for every wave
    moveTimer.start();
    
    ActionListener saucerGenerator = new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        if(game){
          int m = ((int)(Math.random()*40)+1); //picks a random number to see if a sacuer will be generated
          if(m==7){ 
            if(newSaucer == false){ //makes sure a saucer iisn't already on screen
            newSaucer = true;//generates a saucer
            sound.playUFO();//plays saucer sound
            }
          }
        }
      }
      };
      Timer sauceTimer = new Timer(500 , saucerGenerator); 
      sauceTimer.start();
    
    moveBullet();
    
  }
  
  public static void rules(){ //the rules routine creates a new JPanel to be the content pane of the window.
                              //this panel conatins pictures, JLabels, and JPanels laid out to show the rules of the game
    JPanel content = new JPanel();
    content.setLayout(new GridLayout(2,1));
    content.setBackground(Color.black);
    JPanel scoreGrid  = new JPanel();
    scoreGrid.setLayout(new GridLayout(4,3));
    scoreGrid.setBackground(Color.black);
    window.setContentPane(content);
    JLabel text = new JLabel
      ("<html>Use the LEFT and RIGHT arrow Keys to move and the SPACE BAR to shoot<br>Press [B] during the game to deploy the barricades<br>Make sure to avoid enemy bullets, and don't let the aliens get too close!<br>Eliminate as many enenmy invaders as possible to get the high score<br>Press [W] in game to play the secret soundtrack</html>");
    //the html in the code allows the JLabel to be printed on multiple lines
    text.setForeground(Color.WHITE);
    text.setFont(new Font("Impact", Font.PLAIN, 35));
    JButton backButton = new JButton ("back");
    backButton.setOpaque(true);
    backButton.setBackground(Color.GREEN);
    backButton.setFont(new Font("Impact", Font.PLAIN, 22));
    backButton.setBorder(null);
    backButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt) {
          mainMenu();
          window.setVisible(true);
          window.setEnabled(true); //refreshes window
        }
    });
    JLabel pMess1 = new JLabel("10 points");
    pMess1.setForeground(Color.WHITE);
    JLabel pMess2 = new JLabel("20 points");
    pMess2.setForeground(Color.WHITE);
    JLabel pMess3 = new JLabel("50 points");
    pMess3.setForeground(Color.WHITE);
    JLabel pMess4 = new JLabel("100 points");
    pMess4.setForeground(Color.WHITE);
    Image image = null;
    Image image2 = null;
    Image image3 = null;
    Image image4 = null;
    try{
    image = ImageIO.read(new File("./img/Untitled-7.png"));
    image2 = ImageIO.read(new File("./img/Untitled-8.png"));
    image3 = ImageIO.read(new File("./img/Untitled-9.png"));
    image4 = ImageIO.read(new File("./img/saucer.png"));
    }
    catch(Exception e){}
    JLabel picLabel = new JLabel(new ImageIcon(image));
    JLabel picLabel2 = new JLabel(new ImageIcon(image2));
    JLabel picLabel3 = new JLabel(new ImageIcon(image3));
    JLabel picLabel4 = new JLabel(new ImageIcon(image4));
    content.add(text);
    scoreGrid.add(picLabel2);
    scoreGrid.add(pMess1);
    scoreGrid.add(new JLabel());
    scoreGrid.add(picLabel);
    scoreGrid.add(pMess2);
    scoreGrid.add(new JLabel());
    scoreGrid.add(picLabel3);
    scoreGrid.add(pMess3);
    scoreGrid.add(new JLabel());
    scoreGrid.add(picLabel4);
    scoreGrid.add(pMess4);
    scoreGrid.add(backButton);
    content.add(scoreGrid);
    window.setVisible(true);
    window.setEnabled(true); //refreshes window to show the rules
  }
  
  public static void printLoseMessage(Graphics g){ //draws this text on the screen when the game is over
    g.setFont(new Font("Impact", Font.PLAIN, 72));
    g.setColor(Color.WHITE);
    g.drawString("GAME OVER", 320, 200);
    g.setFont(new Font("Impact", Font.PLAIN, 32));
    g.drawString("Press esc to return to the main menu", 250, 250);
  }
  
  public static void printHealth(Graphics g){ //draw the health of the ship, score and highscore in the top of the frame
                                              //constantly updating to refresh the score, and to check if the user's current score is the highscore
    if(score >= highscore.getScoreInt())
      highscore.setScore(score); //sets the score as the highscore in game, so the the highscore can constantly update during gameplay
    g.setFont(new Font("Impact", Font.PLAIN, 32));
    g.setColor(Color.WHITE);
    g.drawString("Lives: " , 28, 28);
    g.drawString("Score: " + score , 830, 28);
    g.drawString("Highscore: " + highscore.getScore(), 450,28);
    int e = health;
    if (health < 7){ //draws the health as ship icons
    for (int i = 0 ; i<health; i++)
      ship.drawHealthShip(g,(i* 55) + 110,0);
    }
    else{ //if there are too many ship icons to be shown, a number ([^] x 7) will be shown instead of individual ships
      ship.drawHealthShip(g,110,0);
      g.drawString(" x " + health, 160,28);
    }
  }
  
  public static void reset(){ //resets variables,and timers to how they were at the begining of the game
    for(int i = 0; i<5; i++){
      for(int j = 0; j<12; j++){
        invaders[j][i].reset();
        hit[j][i] = false;
        moveTimer.stop();
        moveTimer.setDelay(fleetSpeed); //changes the delay, will speed up if a new level has been reached
        moveTimer.start();
      }
    }
    barricades = false;
    kdratio = 0;
  }
  
  public static void moveBullet(){
    int bulletDelay = 15;
    
    ActionListener taskPerformer = new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        if(bullet == true){
          bulletY = bulletY - 10 ;
          if(bulletY < 0){
            bulletY = 490;
            bullet = false;
          }
        }
      }
    };
    Timer timer = new Timer(bulletDelay, taskPerformer); //if a bullet is created, this routine moves the bullet 10 pixels up, every 15 miliseconds
    timer.start();
  }
  
  static class Bullet{
    void draw(Graphics g) {
      int centerY = bulletY;
      Rectangle rect = new Rectangle(xShot , centerY , 4, 10); //creates a rectangle to see if the bullet intersects with anything else on the screen
      if(rect.intersects(saucer.getBounds())){ //if the bullet hits the sacuer
        newSaucer = false; //deletes the saucer
        score += 100; //adds points to the score
        bullet = false; //strops the bullet from moving
        sound.stopUFO(); //stops the UFO from making noise
        sound.playInvaderHit();
        try{
          Image explosion = ImageIO.read(new File("./img/invaderexplosion.png"));
          g.drawImage(explosion, saucer.getX() , 28 , null); //draws the explosion where the saucer was hit
        }
        catch(Exception e){
          System.out.print("image not read");
        }
        saucer.reset(); //resets the saucer to its origoinal position
      }
      if(barricades){ //if the barricades are up
      for(int h = 0 ; h<4 ; h++){
        if(rect.intersects(base[h].getBounds(h* 300))){
         baseInt[h] --; //subtracts from health
         if(baseInt[h] > 0) 
        bullet = false; //stops the bullet from moving if the the barricade has not been broken down yet
        }
      }
      }
      loop : for(int i = 0; i<5; i++){
        for(int j = 0; j<12; j++){
          if(rect.intersects(invaders[j][i].getBounds(j * 47, i * 35))){ //loops through each invader to chech if the bullet has hit one
            if(hit[j][i] == false){
              sound.playInvaderHit(); //plays sound effect
              try{
                Image explosion = ImageIO.read(new File("./img/invaderexplosion.png")); //draws the explosion where impat was made
                g.drawImage(explosion, xShot -25 , bulletY - 45 , null);
              }
              catch(Exception e){
                System.out.print("image not read");
              }
              if(i == 4 || i ==3)
                score += 10;
              if(i == 2 || i == 1)
                score += 20;
              if( i == 0)
                score += 50;//adds points to the score
                kdratio++; //adds 1 to kill count
                hit[j][i] = true;
                bullet = false;
                break loop; //stops looping through th invaders
              }
            }
          }
      }
      g.setColor(Color.RED);
      g.fillRect(xShot , centerY , 4, 10); //draws the rectangle used as a bullet for the ship
    }
  }
  
  static class InvaderShot{
    
    void draw(Graphics g){
      Rectangle rect = new Rectangle(invaderShot + 34, inShotY + 50 , 4, 10); //creates a rectangle to see if the bullet intersects with anything else on the screen
      if(barricades){ //if the barricades are up
      for(int h = 0 ; h<4 ; h++){
        if(rect.intersects(base[h].getBounds(h* 300))){
        baseInt[h] --; //subtracts from health
         if(baseInt[h] > 0)
           inShotY = 700;  //stops the bullet from moving and resets the bullet's location if the the barricade has not been broken down yet
        }
      }
      }
      if(rect.intersects(ship.getBounds())){ //if the bullet has hit a ship
        health --; //health of the ship is decreased
        sound.playShipHit(); //plays the sound for a ship hit
        try{
      Image explosion = ImageIO.read(new File("./img/64px-SpaceInvadersAlienExplosionDepiction copy.png"));
      g.drawImage(explosion, invaderShot, inShotY + 50 , null); //draws an explosoin image on top of the ship at the spot where the bullet intersects the ship
    }
    catch(Exception e){
      System.out.print("image not read");
    }
    inShotY = 700; //resets bullet location
      }
      else{
        g.setColor(Color.WHITE);
        g.fillRect(invaderShot + 34 , inShotY + 50 , 4, 10); //draws rectangle for bullet
      }
    }
    
    void moveShot(){
      inShotY = inShotY + 5 ; //moves the shot down 5 every time moveShot is called
      if(inShotY > 600){ //if the bullet has travels off frame and has not hit anything
        newShot = true;
        invaderBullet = false;
        inShotY = 50; //resets bullet location
        while(true){
          int m = ((int)(Math.random()*12)+1) - 1; //random location for bullets
          int n = ((int)(Math.random()*5)+1) - 1; //random location for bullets
          if(hit[m][n]==false){ //if the invaders int the grid specified by the random numbers was not already hit, a new bullet is shot from its coordinates
            invaderShot = invaders[m][n].getFleetX() + m * 47;
            inShotY = invaders[m][n].getFleetY() + n * 35;
            invaderBullet = true;
            break; //stops looping to find a new shot location
          }
          else if(kdratio == 60)
            break; //if all invaders have been hit bullets are no longer generated
          else
            continue;
        }
      }
    }
  }
}