package tile;

import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.BufferedReader;

public class ManageTiles{
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[] [];
    
    public ManageTiles(GamePanel gp){
        this.gp = gp;
        tile = new Tile[50];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        
        getTileImage();
        loadMapFile("/res/maps/world03.txt");
    }


    public void getTileImage(){
        //placeholders (nullpointer exception)
        setup(0, "grass00", false);
        setup(1, "grass00", false);
        setup(2, "grass00", false);
        setup(3, "grass00", false);
        setup(4, "grass00", false);
        setup(5, "grass00", false);
        setup(6, "grass00", false);
        setup(7, "grass00", false);
        setup(8, "grass00", false);
        setup(9, "grass00", false);

        //actual tiles i'm using
        setup(10, "grass00", false);
        setup(11, "grass01", false);
        setup(12, "water00", true);
        setup(13, "water01", true);
        setup(14, "water02", true);
        setup(15, "water03", true);
        setup(16, "water04", true);
        setup(17, "water05", true);
        setup(18, "water06", true);
        setup(19, "water07", true);
        setup(20, "water08", true);
        setup(21, "water09", true);
        setup(22, "water10", true);
        setup(23, "water11", true);
        setup(24, "water12", true);
        setup(25, "water13", true);
        setup(26, "road00", false);
        setup(27, "road01", false);
        setup(28, "road02", false);
        setup(29, "road03", false);
        setup(30, "road04", false);
        setup(31, "road05", false);
        setup(32, "road06", false);
        setup(33, "road07", false);
        setup(34, "road08", false);
        setup(35, "road09", false);
        setup(36, "road10", false);
        setup(37, "road11", false);
        setup(38, "road12", false);
        setup(39, "earth", false);
        setup(40, "wall", true);
        setup(41, "tree", true);

    }

    public void setup(int index, String imageName, boolean collision){
        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/new/" + imageName +".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public void loadMapFile(String filePath){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); //use the BufferedReader to read the txt
            
            int col = 0;
            int row = 0;
            
            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();
                
                while(col < gp.maxWorldCol){
                    String numbers[] = line.split(" "); //split the lines and get the actual tile numbers one by one
                    
                    int num = Integer.parseInt(numbers[col]);  //change from String to int
                    
                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        }catch(Exception e){
            
        }
    }
    
    public void draw(Graphics2D g2){
        
        //row 1
        /**
        g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null); //one tile is 48x48px
        g2.drawImage(tile[0].image, 48, 0, gp.tileSize, gp.tileSize, null); //2
        g2.drawImage(tile[0].image, 96, 0, gp.tileSize, gp.tileSize, null); //3
        g2.drawImage(tile[0].image, 144, 0, gp.tileSize, gp.tileSize, null); //4
        g2.drawImage(tile[0].image, 192, 0, gp.tileSize, gp.tileSize, null); //5
        g2.drawImage(tile[0].image, 240, 0, gp.tileSize, gp.tileSize, null); //6
        g2.drawImage(tile[0].image, 288, 0, gp.tileSize, gp.tileSize, null); //7
        g2.drawImage(tile[0].image, 336, 0, gp.tileSize, gp.tileSize, null); //8
        
        //row 2
        g2.drawImage(tile[0].image, 0, 48, gp.tileSize, gp.tileSize, null); //1
        g2.drawImage(tile[0].image, 48, 48, gp.tileSize, gp.tileSize, null); //2
        g2.drawImage(tile[0].image, 96, 48, gp.tileSize, gp.tileSize, null); //3
        g2.drawImage(tile[3].image, 144, 48, gp.tileSize, gp.tileSize, null); //4
        g2.drawImage(tile[3].image, 192, 48, gp.tileSize, gp.tileSize, null); //5
        g2.drawImage(tile[0].image, 240, 48, gp.tileSize, gp.tileSize, null); //6
        g2.drawImage(tile[2].image, 288, 48, gp.tileSize, gp.tileSize, null); //7
        g2.drawImage(tile[2].image, 336, 48, gp.tileSize, gp.tileSize, null); //8
        
        //row 3
        g2.drawImage(tile[0].image, 0, 96, gp.tileSize, gp.tileSize, null); //1
        g2.drawImage(tile[0].image, 48, 96, gp.tileSize, gp.tileSize, null); //2
        g2.drawImage(tile[0].image, 96, 96, gp.tileSize, gp.tileSize, null); //3
        g2.drawImage(tile[3].image, 144, 96, gp.tileSize, gp.tileSize, null); //4
        g2.drawImage(tile[3].image, 192, 96, gp.tileSize, gp.tileSize, null); //5
        g2.drawImage(tile[0].image, 240, 96, gp.tileSize, gp.tileSize, null); //6
        g2.drawImage(tile[2].image, 288, 96, gp.tileSize, gp.tileSize, null); //7
        g2.drawImage(tile[0].image, 336, 96, gp.tileSize, gp.tileSize, null); //8
        
        //row 4
        g2.drawImage(tile[3].image, 0, 144, gp.tileSize, gp.tileSize, null); //1
        g2.drawImage(tile[3].image, 48, 144, gp.tileSize, gp.tileSize, null); //2
        g2.drawImage(tile[3].image, 96, 144, gp.tileSize, gp.tileSize, null); //3
        g2.drawImage(tile[3].image, 144, 144, gp.tileSize, gp.tileSize, null); //4
        g2.drawImage(tile[3].image, 192, 144, gp.tileSize, gp.tileSize, null); //5
        g2.drawImage(tile[3].image, 240, 144, gp.tileSize, gp.tileSize, null); //6
        g2.drawImage(tile[3].image, 288, 144, gp.tileSize, gp.tileSize, null); //7
        g2.drawImage(tile[3].image, 336, 144, gp.tileSize, gp.tileSize, null); //8
        
        //row 5
        g2.drawImage(tile[0].image, 0, 192, gp.tileSize, gp.tileSize, null); //1
        g2.drawImage(tile[0].image, 48, 192, gp.tileSize, gp.tileSize, null); //2
        g2.drawImage(tile[0].image, 96, 192, gp.tileSize, gp.tileSize, null); //3
        g2.drawImage(tile[3].image, 144, 192, gp.tileSize, gp.tileSize, null); //4
        g2.drawImage(tile[3].image, 192, 192, gp.tileSize, gp.tileSize, null); //5
        g2.drawImage(tile[0].image, 240, 192, gp.tileSize, gp.tileSize, null); //6
        g2.drawImage(tile[0].image, 288, 192, gp.tileSize, gp.tileSize, null); //7
        g2.drawImage(tile[0].image, 336, 192, gp.tileSize, gp.tileSize, null); //8
        
        //row 6
        g2.drawImage(tile[0].image, 0, 240, gp.tileSize, gp.tileSize, null); //1
        g2.drawImage(tile[0].image, 48, 240, gp.tileSize, gp.tileSize, null); //2
        g2.drawImage(tile[0].image, 96, 240, gp.tileSize, gp.tileSize, null); //3
        g2.drawImage(tile[3].image, 144, 240, gp.tileSize, gp.tileSize, null); //4
        g2.drawImage(tile[3].image, 192, 240, gp.tileSize, gp.tileSize, null); //5
        g2.drawImage(tile[0].image, 240, 240, gp.tileSize, gp.tileSize, null); //6
        g2.drawImage(tile[0].image, 288, 240, gp.tileSize, gp.tileSize, null); //7
        g2.drawImage(tile[0].image, 336, 240, gp.tileSize, gp.tileSize, null); //8
        
        //row 7
        g2.drawImage(tile[0].image, 0, 288, gp.tileSize, gp.tileSize, null); //1
        g2.drawImage(tile[0].image, 48, 288, gp.tileSize, gp.tileSize, null); //2
        g2.drawImage(tile[0].image, 96, 288, gp.tileSize, gp.tileSize, null); //3
        g2.drawImage(tile[3].image, 144, 288, gp.tileSize, gp.tileSize, null); //4
        g2.drawImage(tile[3].image, 192, 288, gp.tileSize, gp.tileSize, null); //5
        g2.drawImage(tile[0].image, 240, 288, gp.tileSize, gp.tileSize, null); //6
        g2.drawImage(tile[0].image, 288, 288, gp.tileSize, gp.tileSize, null); //7
        g2.drawImage(tile[0].image, 336, 288, gp.tileSize, gp.tileSize, null); //8
        
        //row 8
        g2.drawImage(tile[0].image, 0, 336, gp.tileSize, gp.tileSize, null); //1
        g2.drawImage(tile[0].image, 48, 336, gp.tileSize, gp.tileSize, null); //2
        g2.drawImage(tile[0].image, 96, 336, gp.tileSize, gp.tileSize, null); //3
        g2.drawImage(tile[3].image, 144, 336, gp.tileSize, gp.tileSize, null); //4
        g2.drawImage(tile[3].image, 192, 336, gp.tileSize, gp.tileSize, null); //5
        g2.drawImage(tile[0].image, 240, 336, gp.tileSize, gp.tileSize, null); //6
        g2.drawImage(tile[0].image, 288, 336, gp.tileSize, gp.tileSize, null); //7
        g2.drawImage(tile[0].image, 336, 336, gp.tileSize, gp.tileSize, null); //8 **/
        
        
        int worldCol = 0;
        int worldRow = 0;
        
        // world camera idea: https://gamedev.stackexchange.com/questions/44256/how-to-add-a-scrolling-camera-to-a-2d-java-game
        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
            
            
            int tileNum = mapTileNum[worldCol][worldRow];
            
            
            //because its worldCol x worldRow we can do some math :)
            //if the tile is at coordinate 0 he will calculate 0x48 = 0, at coordinate 1, 1x48=48 etc. (so worldX is 48 when worldCol is 1 and worldRow is 0)
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            
            //if statement for performance 
            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

                   g2.drawImage(tile[tileNum].image, screenX, screenY,null);
                }

            worldCol++;

            if (worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }
}