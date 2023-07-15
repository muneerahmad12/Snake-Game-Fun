import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HIEGHT = 600;
    static final int UNIT_SIZE= 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HIEGHT)/UNIT_SIZE;
    static final int DELAY =75;
    final int x[] = new int[GAME_UNITS];
    final int y[]= new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    static char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

   GamePanel(){
    random = new Random();
    this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HIEGHT));
    this.setBackground(Color.black);
    this.setFocusable(true);
    this.addKeyListener(new MyKeyAdapter());
      startGame();

   }
   public void startGame(){
   newApple();
   running=true;
   timer = new Timer(DELAY, this);
   timer.start();
   }
   public void paintComponent(Graphics g){
    super.paintComponent(g);
    draw(g);
   }
   public void draw(Graphics g){
   if(running) {
       g.setColor(Color.RED);
       g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
       for (int i = 0; i < bodyParts; i++) {
           if (i == 0) {
               g.setColor(Color.green);
               g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
           } else {
               g.setColor(new Color(45, 180, 0));
               g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
           }
       }
   }
   else{
       gameOver(g);
   }
   }
   public void newApple(){
       appleX= random.nextInt((int )(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
       appleY= random.nextInt((int )(SCREEN_HIEGHT/UNIT_SIZE))*UNIT_SIZE;

   }
   public void move(){
   for(int i=bodyParts; i>0; i--){
       x[i]=x[i-1];
       y[i]=y[i-1];
   }
       switch (direction) {
           case 'U' -> y[0] -= UNIT_SIZE;
           case 'D' -> y[0] += UNIT_SIZE;
           case 'L' -> x[0] -= UNIT_SIZE;
           case 'R' -> x[0] += UNIT_SIZE;
       }
   }
   public void checkApple(){
      if(x[0]==appleX && y[0]==appleY){
          bodyParts++;
          applesEaten++;
          newApple();
      }
   }
   public void checkCollisions() {
       for (int i = bodyParts; i > 0; i--) {
           if (x[0] == x[i] && y[0] == y[i]) {
               running = false;
           }
       }
       if (x[0] < 0) {
           running = false;
       }
       if (x[0] > SCREEN_WIDTH) {
           running = false;
       }
       if (y[0] < 0) {
           running = false;
       }
       if (y[0] > SCREEN_HIEGHT) {
           running = false;
       }
       if(!running)
           timer.stop();
   }
   public void gameOver(Graphics g){
    g.setColor(Color.RED);
    g.setFont(new Font("Ink Free", Font.BOLD, 50));
    FontMetrics metrics = getFontMetrics(g.getFont());
    g.drawString("Game Over", (SCREEN_WIDTH-metrics.stringWidth("Game Over"))/2, SCREEN_HIEGHT/2);
    String str = Integer.toString(applesEaten);
    g.drawString("Apples Eaten "+str, 100, 100);
   }
    @Override
    public void actionPerformed(ActionEvent e) {
   if(running){
       move();
       checkApple();
       checkCollisions();
   }
   repaint();
    }
    public static class MyKeyAdapter extends KeyAdapter {
       @Override
        public  void keyPressed(KeyEvent e){
           switch (e.getKeyCode()) {
               case KeyEvent.VK_LEFT -> {
                   if (direction != 'R') {
                       direction = 'L';
                   }
               }
               case KeyEvent.VK_RIGHT -> {
                   if (direction != 'L') {
                       direction = 'R';
                   }
               }
               case KeyEvent.VK_UP -> {
                   if (direction != 'D') {
                       direction = 'U';
                   }
               }
               case KeyEvent.VK_DOWN -> {
                   if (direction != 'u') {
                       direction = 'D';
                   }
               }
           }
       }
    }
}
