package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;
        
        screenX = gp.screenWidth/2 - (gp.tileSize/2); //display character in center of screen (anchor point top left)
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        
        hitBox = new Rectangle();
        hitBox.x = 8;
        hitBox.y = 16;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
        hitBox.width = 32;
        hitBox.height = 32;
        
        setDefaultState();
        getImage();
    }
    
    public void setDefaultState(){
        worldX=1103;
        worldY=1000;
        speed=3;
        direction = "down"; //change default direction here [up, down, left, right]

        maxLife = 6;
        life = maxLife;
    }
    
    public void getImage(){ //https://stackoverflow.com/questions/27933159/how-to-draw-an-bufferedimage-to-a-jpanel  <-- refactored my code and looks a lot different now
        String pathHead = "/player/boy_";

        up1 = setup(pathHead+"up_1");
        up2 = setup(pathHead+"up_2");
        down1 = setup(pathHead+"down_1");
        down2 = setup(pathHead+"down_2");
        left1 = setup(pathHead+"left_1");
        left2 = setup(pathHead+"left_2");
        right1 = setup(pathHead+"right_1");
        right2 = setup(pathHead+"right_2");


    }


    public void update(){ //update gets called 60 times per second (gameloop -> main.GamePanel)
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed){
            if(keyH.upPressed){
                direction = "up";

            }
            else if(keyH.downPressed){
                direction = "down";

            }
            else if(keyH.leftPressed){
                direction = "left";

            }
            else if (keyH.rightPressed){
                direction = "right";
            }


            //check tile collison
            collisionOn = false;
            gp.cd.checkTile(this);

            //check object collision
            int objIndex = gp.cd.checkObject(this, true);
            pickUpObj(objIndex);

            //check npc collision
            int npcIndex = gp.cd.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //check monster collision
            int monsterIndex = gp.cd.checkEntity(this, gp.monster);
            interactMonster(monsterIndex);

            //check event
            gp.eHandler.checkEvent();


            if(!collisionOn && !keyH.enterPressed){
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            gp.keyH.enterPressed = false;

            spriteCounter++;
            if(spriteCounter > 13){
                if(spriteNumber == 1){
                    spriteNumber = 2;
                }
                else if(spriteNumber == 2){
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }
        }
        if (invincible){
            invincibleCounter++;
            if (invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    private void interactMonster(int i) {
        if (i != 69){
            if (!invincible){
                life -= 1;
                invincible = true;
            }
        }
    }

    public void pickUpObj(int i){
        if(i != 69){
            //stuff
        }
    }

    public void interactNPC(int i){
        if(i != 69){
            if (gp.keyH.enterPressed){
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }


    }

    public void draw(Graphics2D g2){
        //g2.setColor(Color.WHITE);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);
        
        BufferedImage image = null;
        
        if(direction == "up"){
            if(spriteNumber == 1){
                image = up1;
            }
            else if(spriteNumber == 2){
                image = up2;
            }
        }
        else if(direction == "down"){
            if(spriteNumber == 1){
                image = down1;
            }
            else if(spriteNumber == 2){
                image = down2;
            }
        }
        else if(direction == "left"){
            if(spriteNumber == 1){
                image = left1;
            }
            else if(spriteNumber == 2){
                image = left2;
            }
        }
        else if(direction == "right"){
            if(spriteNumber == 1){
                image = right1;
            }
            else if(spriteNumber == 2){
                image = right2;
            }
        }

        if (invincible){
            if (invincibleCounter <=20 || invincibleCounter >40) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            }
        }

        g2.drawImage(image, screenX, screenY, null); //"null" ist der ImageObserver, kein plan wirklich was der macht aber ich glaube den brauchen wir nicht ;)

    }
}