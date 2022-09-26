package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }
    
    @Override
    public void keyTyped(KeyEvent e){
    
    }
    
    @Override
    public void keyPressed(KeyEvent e){

        int code = e.getKeyCode();
        if (gp.ui.titleScreenState == 0){
                if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                    gp.ui.commandNum--;
                    if(gp.ui.commandNum < 0){
                        gp.ui.commandNum = 2;
                    }
                }
                if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                    gp.ui.commandNum++;
                    if(gp.ui.commandNum > 2){
                        gp.ui.commandNum = 0;
                    }
                }
                if(code == KeyEvent.VK_ENTER){
                    if (gp.ui.commandNum == 0){
                        gp.ui.titleScreenState = 1;
                    }else if(gp.ui.commandNum == 1){
                        //add later
                    }else if (gp.ui.commandNum == 2){
                        System.exit(0);
                    }
                }
        }
        else if (gp.ui.titleScreenState == 1){
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 3;
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 3){
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                if (gp.ui.commandNum == 0){
                    gp.gameState = gp.playState;
                    gp.stopMusic();
                    gp.playTheme(0);
                    gp.ui.titleScreenState = 3; //title screen state 3 is just a placeholder, because we dont want enter to replay the music when we're in game
                }else if(gp.ui.commandNum == 1){
                    gp.gameState = gp.playState;
                    gp.stopMusic();
                    gp.playTheme(0);
                    gp.ui.titleScreenState = 3;
                }else if (gp.ui.commandNum == 2){
                    gp.gameState = gp.playState;
                    gp.stopMusic();
                    gp.playTheme(0);
                    gp.ui.titleScreenState = 3;
                }else if (gp.ui.commandNum == 3){
                    gp.ui.titleScreenState = 0;
                }
            }
        }

        if (gp.gameState == gp.playState){
            if(code == KeyEvent.VK_W){
                upPressed = true;
            }
            if(code == KeyEvent.VK_A){
                leftPressed = true;
            }
            if(code == KeyEvent.VK_S){
                downPressed = true;
            }
            if(code == KeyEvent.VK_D){
                rightPressed = true;
            }
            if(code == KeyEvent.VK_ESCAPE){
                gp.gameState = gp.pauseState;
            }
            if(code == KeyEvent.VK_ENTER){
                enterPressed = true;
            }

            //debug
            if(code == KeyEvent.VK_T){
                checkDrawTime = !checkDrawTime;
            }
        } else if(gp.gameState == gp.pauseState){
            if(code == KeyEvent.VK_ESCAPE){
                gp.gameState = gp.playState;
            }
        } else if(gp.gameState == gp.dialogueState){
            if(code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
        }


    }
    
    @Override
    public void keyReleased(KeyEvent e){
        int code = e.getKeyCode();
        
        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
    }
    
}