 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tacotango;

import environment.Environment;
import environment.LocationValidatorIntf;
import grid.Grid;
import images.ResourceTools;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import audio.AudioPlayer;
import java.awt.Dimension;

/**
 *
 * @author carateresa
 */
class TacoTangoEnvironment extends Environment implements GridDrawData, LocationValidatorIntf {

    private Grid grid;
    private Taco taco;
    private Image segmentImage, gameOverScreen;
    private Image flag;
    private Score score;
    private int Level;

    public final int SLOW_SPEED = 4;
    public final int SLOWMEDIUM_SPEED = 3;
    public final int MEDIUM_SPEED = 2;
    public final int HIGHMEDIUM_SPEED = 1;
    public final int HIGH_SPEED = 1 / 8;

    private int moveDelayLimit = SLOW_SPEED;
    private int moveDelayCounter = 0;

    private ArrayList<GridObject> gridObjects;

    private GameState gameState = GameState.OVER;

    public TacoTangoEnvironment() {

    }

    @Override
    public void initializeEnvironment() {
        score = new Score();
        score.setPosition(new Point(10, 10));

        flag = ResourceTools.loadImageFromResource("resources/TortillaVeryTransparent.png");
        gameOverScreen = ResourceTools.loadImageFromResource("resources/GameOver.gif");
        
//        this.setBackground(ResourceTools.loadImageFromResource("resources/background_mexican.jpg").getScaledInstance(900, 700, Image.SCALE_FAST));
        setLevel(1);
        grid = new Grid(34, 17, 25, 25, new Point(25, 250), Color.BLACK);

//        grid.setPosition(new Point (20, 200));
        taco = new Taco();
        taco.setDirection(Direction.DOWN);
        taco.setDrawData(this);
        taco.setLocationValidator(this);

        ArrayList<Point> body = new ArrayList<>();
        body.add(new Point(3, 1));
        body.add(new Point(3, 2));
        body.add(new Point(2, 2));
        body.add(new Point(2, 3));

        taco.setGrowthCounter(3);
        taco.setBody(body);

        gridObjects = new ArrayList<GridObject>();
        gridObjects.add(new GridObject(GridObjectType.FLAG, getRandomPoint()));
        gridObjects.add(new GridObject(GridObjectType.FLAG, getRandomPoint()));
        gridObjects.add(new GridObject(GridObjectType.FLAG, getRandomPoint()));
        gridObjects.add(new GridObject(GridObjectType.FLAG, getRandomPoint()));

    }

    @Override
    public void timerTaskHandler() {
        if (taco != null) {
//            if counter >= limit then reset counter and move snake
//            else increment counter
            if (moveDelayCounter >= moveDelayLimit) {
                moveDelayCounter = 0;
                taco.move();

            } else {
                moveDelayCounter++;
            }

        }

    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_C) {
            grid.setShowCellCoordinates(!grid.getShowCellCoordinates());

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            taco.setDirection(Direction.LEFT);

        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            taco.setDirection(Direction.DOWN);

        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            taco.setDirection(Direction.RIGHT);

        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            taco.setDirection(Direction.UP);

        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            taco.togglePaused();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            taco.grow(2);
        } else if (e.getKeyCode() == KeyEvent.VK_M) {
            AudioPlayer.play("/resources/OleSoundMexico.wav");

        } else if (e.getKeyCode() == KeyEvent.VK_1) {
            setLevel(1);
        } else if (e.getKeyCode() == KeyEvent.VK_2) {
            setLevel(2);
        } else if (e.getKeyCode() == KeyEvent.VK_3) {
            setLevel(3);
        } else if (e.getKeyCode() == KeyEvent.VK_4) {
            setLevel(4);
        } else if (e.getKeyCode() == KeyEvent.VK_5) {
            setLevel(5);

        }else if (e.getKeyCode() == KeyEvent.VK_6) {
            setLevel(6);
            
    }else if (e.getKeyCode() == KeyEvent.VK_S) {
//        case : START;
    }
    }

    @Override
    public void keyReleasedHandler(KeyEvent e
    ) {

    }

    @Override
    public void environmentMouseClicked(MouseEvent e
    ) {

    }

    @Override
    public void paintEnvironment(Graphics graphics) {
        switch (gameState) {
            case START:
                
                

                break;

            case PLAYING:

                if (score != null) {
                    score.draw(graphics);
                }

                if (grid != null) {
                }

                if (taco != null) {
                    taco.draw(graphics);
                }

                if (gridObjects != null) {
                    for (GridObject gridObject : gridObjects) {
                        if (gridObject.getType() == GridObjectType.FLAG) {
                            graphics.drawImage(flag, grid.getCellSystemCoordinate(gridObject.getLocation()).x,
                                    grid.getCellSystemCoordinate(gridObject.getLocation()).y,
                                    grid.getCellHeight(), grid.getCellWidth(), this);

                            break;

                        }
                    }
                }
            case OVER:
                graphics.drawImage(gameOverScreen, 0, 0, this);
        }

//        if (score != null) {
//            score.draw(graphics);
//        }
//
//        if (grid != null) {
////            grid.paintComponent(graphics);
//        }
//
//        if (taco != null) {
//            taco.draw(graphics);
//        }
//
//        if (gridObjects != null) {
//            for (GridObject gridObject : gridObjects) {
//                if (gridObject.getType() == GridObjectType.FLAG) {
//                    graphics.drawImage(flag, grid.getCellSystemCoordinate(gridObject.getLocation()).x,
//                            grid.getCellSystemCoordinate(gridObject.getLocation()).y,
//                            grid.getCellHeight(), grid.getCellWidth(), this);
////                    segmentImage = ResourceTools.loadImageFromResource("resources/taco.png");               
////                    segmentImage(ResourceTools.loadImageFromResource("resources/taco.png"), grid.getCellSystemCoordinate(gridObject.getLocation()), grid.getCellSize());
//
//                }
//
//            }
    }

    /**
     * @return a Random Point
     */
    public Point getRandomPoint() {
        return new Point((int) ((grid.getColumns() - 1) * Math.random()), (int) (grid.getRows() * Math.random()));
    }

    //<editor-fold defaultstate="collapsed" desc="GridDrawData Interface">
    @Override
    public int getCellHeight() {
        return grid.getCellHeight();
    }

    @Override
    public int getCellWidth() {
        return grid.getCellWidth();
    }

    @Override
    public Point getCellSystemCoordinate(Point cellCoordinate) {
        return grid.getCellSystemCoordinate(cellCoordinate);

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="LocationValidatorIntf">
    @Override
    public Point validateLocation(Point point) {

        if (taco.isSelfHit()) {
            taco.setPaused(true);
        }

        if (point.x >= this.grid.getColumns()) {
            point.x = 0;
        } else if (point.x < 0) {
            point.x = this.grid.getColumns() - 1;
        }

        if (point.y >= this.grid.getRows()) {
            point.y = 0;
        } else if (point.y < 0) {
            point.y = this.grid.getRows() - 1;
        }

        // check if the snake hit a GridObject, then take the appropriate action:
        // Flag = grow snake by 3. American flag = make sound, kill snake.
        // look at all the locations stored in the gridObject ArrayList
        // for each, compare it to the head location stored in the "point" parameter
        for (GridObject object : gridObjects) {
            if (object.getLocation().equals(point)) {
                System.out.println("Hit = " + object.getType());
                object.setLocation(this.getRandomPoint());
                System.out.println("Position = " + object.getLocation());
                AudioPlayer.play("/resources/OleSoundMexico.wav");
                taco.grow(2);
                this.score.addToValue(1);

            }

        }

        return point;

    }

    //</editor-fold>
    /**
     * @return the Level
     */
    public int getLevel() {
        return Level;
    }

    /**
     * @param Level the Level to set
     */
    public void setLevel(int Level) {
        if (this.Level != Level) {
            if (Level == 1) {
                this.setBackground(ResourceTools.loadImageFromResource("resources/background_mexican.jpg").getScaledInstance(900, 700, Image.SCALE_FAST));

            } else if (Level == 2) {
                this.setBackground(ResourceTools.loadImageFromResource("resources/Level2background_mexican.jpg").getScaledInstance(900, 700, Image.SCALE_FAST));
                flag = ResourceTools.loadImageFromResource("resources/MeatVeryTransparent.png");
                moveDelayLimit = SLOWMEDIUM_SPEED;

            } else if (Level == 3) {
                this.setBackground(ResourceTools.loadImageFromResource("resources/Level3background_mexican.jpg").getScaledInstance(900, 700, Image.SCALE_FAST));
                flag = ResourceTools.loadImageFromResource("resources/SalsaVeryTransparent.png");
                moveDelayLimit = MEDIUM_SPEED;

            } else if (Level == 4) {
                this.setBackground(ResourceTools.loadImageFromResource("resources/Level4background_mexican.jpg").getScaledInstance(900, 700, Image.SCALE_FAST));
                flag = ResourceTools.loadImageFromResource("resources/JalapenoTransparent.png");
                moveDelayLimit = HIGHMEDIUM_SPEED;

            } else if (Level == 5) {
                this.setBackground(ResourceTools.loadImageFromResource("resources/Level5background_mexican.jpg").getScaledInstance(900, 700, Image.SCALE_FAST));
                flag = ResourceTools.loadImageFromResource("resources/MexicoFlagIcon.jpg");
                moveDelayLimit = HIGH_SPEED;

            

        this.Level = Level;
    }

}
    }
}

