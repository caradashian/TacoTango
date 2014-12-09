/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tacotango;

import environment.LocationValidatorIntf;
import images.ResourceTools;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author carateresa
 */
public class Taco {

    private ArrayList<Point> body = new ArrayList<>();
    private Direction direction = Direction.RIGHT;
    private GridDrawData drawData;
    private LocationValidatorIntf locationValidator;
    private boolean paused;
    private Image segmentImage;
    private int growthCounter;

//    grow
//    eat
//    move
//    die
//    draw
    {
        segmentImage = ResourceTools.loadImageFromResource("resources/taco.png");
    }

    public void draw(Graphics graphics) {
        Image segment = segmentImage.getScaledInstance(drawData.getCellWidth(), drawData.getCellHeight(), Image.SCALE_FAST);

        for (Point bodySegmentLocation : getSafeBody()) {
            Point topLeft = drawData.getCellSystemCoordinate(bodySegmentLocation);

            graphics.setColor(Color.RED);
//            graphics.fillOval(topLeft.x, topLeft.y, 
//                    drawData.getCellWidth(), drawData.getCellHeight());
            graphics.drawImage(segment, topLeft.x, topLeft.y, null);
        }

    }

    /**
     * @return the body
     */
    public ArrayList<Point> getSafeBody() {
        ArrayList<Point> safeBody = new ArrayList<Point>();
        for (Point point : body) {
            safeBody.add(point);
        }
        return safeBody;
    }

    /**
     * @return the body
     */
    public ArrayList<Point> getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(ArrayList<Point> body) {
        this.body = body;
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * @return the drawData
     */
    public GridDrawData getDrawData() {
        return drawData;
    }

    /**
     * @param drawData the drawData to set
     */
    public void setDrawData(GridDrawData drawData) {
        this.drawData = drawData;
    }

    private final int HEAD_POSITION = 0;

    public void grow(int length) {
        growthCounter += length;

    }

    public void move() {
        if (!paused) {

            Point newHead = (Point) getHead().clone();

            if (direction == Direction.DOWN) {
                newHead.y++;
            } else if (direction == Direction.UP) {
                newHead.y--;
            } else if (direction == Direction.LEFT) {
                newHead.x--;
            } else if (direction == Direction.RIGHT) {
                newHead.x++;
            }

            if (locationValidator != null) {
                body.add(HEAD_POSITION, locationValidator.validateLocation(newHead));
            }

            if (growthCounter <= 0) {
                body.remove(body.size() - 1);
            } else {
                growthCounter--;

            }
            
            

        }
        

    }

    public Point getHead() {
        return body.get(HEAD_POSITION);
    }

    /**
     * @return the locationValidator
     */
    public LocationValidatorIntf getLocationValidator() {
        return locationValidator;
    }

    /**
     * @param locationValidator the locationValidator to set
     */
    public void setLocationValidator(LocationValidatorIntf locationValidator) {
        this.locationValidator = locationValidator;
    }

    /**
     *
     */
    public void togglePaused() {
        if (paused) {
            paused = false;
        } else {
            paused = true;
        }
    }

    /**
     * @return the paused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * @param paused the paused to set
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * @return the growthCounter
     */
    public int getGrowthCounter() {
        return growthCounter;
    }

    /**
     * @param growthCounter the growthCounter to set
     */
    public void setGrowthCounter(int growthCounter) {
        this.growthCounter = growthCounter;
    }

}
