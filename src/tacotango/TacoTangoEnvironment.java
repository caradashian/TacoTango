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
import images.Animator;
import images.ImageManager;
import java.awt.Dimension;

/**
 *
 * @author carateresa
 */
class TacoTangoEnvironment extends Environment implements GridDrawData, LocationValidatorIntf {

    private Grid grid;
    private Taco taco;
    private Image segmentImage, gameOverScreen, startPage;
    private Image flag;
    private Score score;
    private int Level;

    public final int SLOW_SPEED = 4;
    public final int SLOWMEDIUM_SPEED = 3;
    public final int MEDIUM_SPEED = 2;
    public final int HIGHMEDIUM_SPEED = 1;
    public final int HIGH_SPEED = 1 / 8;
    public static int LEVEL_OVER = 6;

    private int moveDelayLimit = SLOW_SPEED;
    private int moveDelayCounter = 0;

    private ArrayList<GridObject> gridObjects;

    private ImageManager imageManager;
    private Animator animator;

    private GameState gameState;// = GameState.PLAYING;
    private ArrayList<String> gameOverAnimationList;

    public TacoTangoEnvironment() {

    }

    @Override
    public void initializeEnvironment() {
        score = new Score();
        score.setPosition(new Point(10, 10));

        flag = ResourceTools.loadImageFromResource("resources/TortillaVeryTransparent.png");

//        this.setBackground(ResourceTools.loadImageFromResource("resources/background_mexican.jpg").getScaledInstance(900, 700, Image.SCALE_FAST));
        setLevel(1);
        grid = new Grid(34, 17, 25, 25, new Point(25, 250), Color.BLACK);

        imageManager = new ImageManager();
        imageManager.addImage("GAMEOVER_01", ResourceTools.loadImageFromResource("resources/game_over_g.jpg"));
        imageManager.addImage("GAMEOVER_02", ResourceTools.loadImageFromResource("resources/game_over_ga.jpg"));
        imageManager.addImage("GAMEOVER_03", ResourceTools.loadImageFromResource("resources/game_over_gam.jpg"));
        imageManager.addImage("GAMEOVER_04", ResourceTools.loadImageFromResource("resources/game_over_game.jpg"));
        imageManager.addImage("GAMEOVER_05", ResourceTools.loadImageFromResource("resources/game_over_gameo.jpg"));
        imageManager.addImage("GAMEOVER_06", ResourceTools.loadImageFromResource("resources/game_over_gameov.jpg"));
        imageManager.addImage("GAMEOVER_07", ResourceTools.loadImageFromResource("resources/game_over_gameove.jpg"));
        imageManager.addImage("GAMEOVER_08", ResourceTools.loadImageFromResource("resources/game_over_gameover.jpg"));
        imageManager.addImage("GAMEOVER_09", ResourceTools.loadImageFromResource("resources/game_over_none.jpg"));

        gameOverAnimationList = new ArrayList<>();
        gameOverAnimationList.add("GAMEOVER_01");
        gameOverAnimationList.add("GAMEOVER_02");
        gameOverAnimationList.add("GAMEOVER_03");
        gameOverAnimationList.add("GAMEOVER_04");
        gameOverAnimationList.add("GAMEOVER_05");
        gameOverAnimationList.add("GAMEOVER_06");
        gameOverAnimationList.add("GAMEOVER_07");
        gameOverAnimationList.add("GAMEOVER_08");
        gameOverAnimationList.add("GAMEOVER_09");
        gameOverAnimationList.add("GAMEOVER_08");
        gameOverAnimationList.add("GAMEOVER_09");
        gameOverAnimationList.add("GAMEOVER_08");
        gameOverAnimationList.add("GAMEOVER_09");
        gameOverAnimationList.add("GAMEOVER_08");

//            
        animator = new Animator(imageManager, gameOverAnimationList, 400);
//      
//        grid.setPosition(new Point (20, 200));
        taco = new Taco();
        taco.setDirection(Direction.DOWN);
        taco.setDrawData(this);
        taco.setLocationValidator(this);

        ArrayList<Point> body = new ArrayList<>();

        body.add(new Point(3, 1));
//        body.add(new Point(3, 2));
//        body.add(new Point(2, 2));
//        body.add(new Point(2, 3));

        taco.setGrowthCounter(3);
        taco.setBody(body);
        taco.setDirection(Direction.RIGHT);
        taco.setGrowthCounter(5);

        gridObjects = new ArrayList<GridObject>();
        gridObjects.add(new GridObject(GridObjectType.FLAG, getRandomPoint()));
        gridObjects.add(new GridObject(GridObjectType.FLAG, getRandomPoint()));
        gridObjects.add(new GridObject(GridObjectType.FLAG, getRandomPoint()));
        gridObjects.add(new GridObject(GridObjectType.FLAG, getRandomPoint()));

        gameState = GameState.START;
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

        } else if (e.getKeyCode() == KeyEvent.VK_O) {
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

        } else if (e.getKeyCode() == KeyEvent.VK_6) {
            setLevel(6);

        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            gameState = GameState.PLAYING;

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
                graphics.drawImage(ResourceTools.loadImageFromResource("resources/final_start_page.jpg").getScaledInstance(900, 700, Image.SCALE_FAST), 0, 0, this);

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
                break;

            case OVER:
//                graphics.drawImage(gameOverScreen, 0, 0, this);
                graphics.drawImage(animator.getCurrentImage().getScaledInstance(900, 750, Image.SCALE_FAST), 0, 0, this);
//                graphics.drawi

        }

//        
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
            setLevel(LEVEL_OVER);
            gameState = GameState.OVER;
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

//        this.setLevel();
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
