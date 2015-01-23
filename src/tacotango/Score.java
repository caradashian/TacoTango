/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tacotango;

import java.awt.Color;
import java.awt.Font;

import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author carateresa
 */
public class Score {
    
    public void draw(Graphics graphics){
        graphics.setColor(Color.BLUE);
        graphics.drawString("Score: " + value, position.x, position.y);
        graphics.setFont(font);
        
    }
    private int value = 0;
    private Point position;
    private Font font = new Font ("Papyrus", Font.BOLD, 90);

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }
    
     /**
     * @param value the value to set
     */
    public void addToValue(int amount) {
        this.value += amount;
    }

    /**
     * @return the position
     */
    public Point getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Point position) {
        this.position = position;
    }
    
    
}
