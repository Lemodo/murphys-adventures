package entity;

import main.GamePanel;
import java.util.Random;

public class PapaJoe extends Entity{
    public PapaJoe(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }

    public void getImage(){ //https://stackoverflow.com/questions/27933159/how-to-draw-an-bufferedimage-to-a-jpanel  <-- refactored my code and looks a lot different now
        String pathHead = "/npc/oldman_";

        up1 = setup(pathHead+"up_1");
        up2 = setup(pathHead+"up_2");
        down1 = setup(pathHead+"down_1");
        down2 = setup(pathHead+"down_2");
        left1 = setup(pathHead+"left_1");
        left2 = setup(pathHead+"left_2");
        right1 = setup(pathHead+"right_1");
        right2 = setup(pathHead+"right_2");
    }
    public void setDialogue(){
        dialogues[0] = "Hello mate!";
        dialogues[1] = "I haven't seen anyone here \nin ages.";
        dialogues[2] = "Oh, you're probably here \nfor the treasure...";
        dialogues[3] = "I'll tell you more about \nit once you've found my \nglasses.";
        dialogues[4] = "They should be here \nsomewhere in a chest.";
    }
    public void setAction(){

        actionTick++;

        if (actionTick == 120){
            Random random = new Random();
            int i = random.nextInt(100)+1; //1-100

            if (i <= 25){
                direction = "up";
            }
            if (i > 25 && i <= 50){
                direction = "down";
            }
            if (i > 50 && i <= 75){
                direction = "left";
            }
            if (i > 75){
                direction = "right";
            }
            actionTick = 0;
        }
    }
    public void speak(){
        super.speak();
    }
}
