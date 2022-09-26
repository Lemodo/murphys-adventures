package object;

import entity.Entity;
import main.GamePanel;


public class Door extends Entity {

    public Door(GamePanel gp){
        super(gp);
        
        name = "Door";
        down1 = setup("/objects/door");
        collision = true;

        hitBox.x = 0;
        hitBox.y = 16;
        hitBox.width = 48;
        hitBox.height = 32;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
    }
}