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
    private Image segmentImage;
    private Image flag;
    

    public final int SLOW_SPEED = 7;
    public final int MEDIUM_SPEED = 5;
    public final int HIGH_SPEED = 3;

    private int moveDelayLimit = HIGH_SPEED;
    private int moveDelayCounter = 0;

    private ArrayList<GridObject> gridObjects;

    public TacoTangoEnvironment() {

    }

    @Override
    public void initializeEnvironment() {
        flag = ResourceTools.loadImageFromResource("resources/MexicoFlagIcon.jpg");
        this.setBackground(ResourceTools.loadImageFromResource("resources/background_mexican.jpg").getScaledInstance(900, 700, Image.SCALE_FAST));
        grid = new Grid(34, 17, 25, 25, new Point(25, 250), Color.RED);
        grid.setColor(Color.RED);
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

        }

    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {

    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {

    }

    @Override
    public void paintEnvironment(Graphics graphics) {
        if (grid != null) {
            grid.paintComponent(graphics);
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
//                    segmentImage = ResourceTools.loadImageFromResource("resources/taco.png");               
//                    segmentImage(ResourceTools.loadImageFromResource("resources/taco.png"), grid.getCellSystemCoordinate(gridObject.getLocation()), grid.getCellSize());

                }

            }

        }

    }

    /**
     * @return a Random Point
     */
    public Point getRandomPoint() {
        return new Point((int) (grid.getRows() * Math.random()), (int) (grid.getColumns() * Math.random()));
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

        if (point.x >= this.grid.getColumns()) {
            point.x = 0;
        } else if (point.x < 0) {
            point.x = this.grid.getColumns() - 1;
        }

        if (point.y >= this.grid.getColumns()) {
            point.y = 0;
        } else if (point.y < 0) {
            point.y = this.grid.getColumns() - 1;
        }

        // check if the snake hit a GridObject, then take the appropriate action:
        // Flag = grow snake by 3. American flag = make sound, kill snake.
        // look at all the locations stored in the gridObject ArrayList
        // for each, compare it to the head location stored in the "point" parameter
        for (GridObject object : gridObjects) {
            if (object.getLocation().equals(point)) {
                System.out.println("Hit = " + object.getType());
                object.setLocation(this.getRandomPoint());
                AudioPlayer.play("/resources/OleSoundMexico.wav");
                taco.grow(2);
            }

        }

        return point;

    }
    
    //</editor-fold>

    
}
