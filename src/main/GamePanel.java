package main;

import entity.Entity;
import entity.Player;
import object.PlaceObj;
import tile.ManageTiles;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{
    //settings for the screen
    final int originalTileSize = 16; //16x16 tiles
    final int scale = 3;
    
    public final int tileSize = originalTileSize * scale; //scale to 48x48 tiels
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; //768
    public final int screenHeight = tileSize * maxScreenRow; //576
    
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    
    int fps = 60;
    
    ManageTiles tileM = new ManageTiles(this);
    public KeyHandler keyH = new KeyHandler(this);
    
    Sound music = new Sound();
    Sound sfx = new Sound();
    
    public CollisionDetection cd = new CollisionDetection(this);
    public PlaceObj objPlacer = new PlaceObj(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;
    
    public Player player = new Player(this, keyH);
    public Entity[] obj = new Entity[10]; //prepare 10 object slots (to display up to 10 objects at the same time)
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    //GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int titleState = 0;
    
    
    
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    
    public void setupGame(){
        objPlacer.setObject();
        objPlacer.setNPC();
        objPlacer.setMonster();
        playTheme(6); //0 is the index of the theme song (array)
        gameState = titleState;
    }
    
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override //Annotation
    public void run(){
        
        double drawInterval = 1000000000/fps; //1 second in nano seconds | draw the screen 60 times a second 
        double nextDrawTime = System.nanoTime() + drawInterval;
        
        while(gameThread != null){ //game loop
            long currentTime = System.nanoTime();
            
            update();
            
            repaint();
            
            try{
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000; //Thread.sleep only accepts ms
                
                if (remainingTime < 0){
                    remainingTime = 0;
                }
                
                Thread.sleep((long) remainingTime); //pause game looop
                
                nextDrawTime += drawInterval;
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            
        }
    }
    
    public void update(){
        if(gameState == playState){
            player.update();
            for (Entity entity : npc) { //enhanced for loop
                if (entity != null) {
                    entity.update();
                }
            }
            for (Entity entity : monster) {
                if (entity != null) {
                    entity.update();
                }
            }
        } else if (gameState == pauseState) {

        }

    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        //DEBUG
        long drawStart = 0;
        if(keyH.checkDrawTime){
            drawStart = System.nanoTime();
        }

        //title screen
        if (gameState == titleState){
            ui.draw(g2);
        }else {
            tileM.draw(g2); //draw tiles first

            //add entities to list
            entityList.add(player);
            for (int i = 0; i < npc.length; i++){
                if (npc[i] != null){
                    entityList.add(npc[i]);
                }
            }
            for (int i = 0; i < obj.length; i++){
                if (obj[i] != null){
                    entityList.add(obj[i]);
                }
            }
            for (int i = 0; i < monster.length; i++){
                if (monster[i] != null){
                    entityList.add(monster[i]);
                }
            }

            //sort
            entityList.sort(new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    return Integer.compare(e1.worldY, e2.worldY);
                }
            });

            //draw entities
            for (int i = 0; i < entityList.size(); i++){
                entityList.get(i).draw(g2);
            }
            //empty entities
            entityList.clear();

            ui.draw(g2); //then draw ui on top
        }


        //DEBUG
        if(keyH.checkDrawTime){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw time: "+passed);
        }


        g2.dispose();
    }

    
    public void playTheme(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    
    public void stopMusic(){
        music.stop();
    }

    public void playSFX(int i){
        sfx.setFile(i);
        sfx.play();
    }
} 