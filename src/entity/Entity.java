package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Entity {
    GamePanel gp;
    public int worldX; 
    public int worldY;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; //BufferedImage: describes an Image with a buffer of image data (in this case store our imgages)
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public String direction = "down";
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public Rectangle hitBox = new Rectangle(0, 0, 48, 48);
    public int hitBoxDefaultX, hitBoxDefaultY;
    public boolean collisionOn = false;
    public int actionTick;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    String[] dialogues = new String[20];
    int dialogueIndex = 0;
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;
    public int type; // 0 = player; 1 = npc; 2 = monster etc...

    public int maxLife;
    public int life;

    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public void setAction(){

    }
    public void speak(){
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
    public void update(){
        setAction();

        collisionOn = false;
        gp.cd.checkTile(this);
        gp.cd.checkObject(this, false);
        gp.cd.checkEntity(this, gp.npc);
        gp.cd.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cd.checkPlayer(this);

        if (this.type == 2 && contactPlayer){
            if (!gp.player.invincible){
                gp.player.life -= 1;
                gp.player.invincible = true;
            }
        }

        if(!collisionOn){
            switch (direction){
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }

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

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

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

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    public BufferedImage setup(String imagePath){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
}
