/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sasav.blackjack.model.game;

/**
 *
 * @author sasav
 */
public class GamePoints {
    private int min;
    private int max;
    private boolean bj;

    public GamePoints() {
    }

    public GamePoints(int min, int max, boolean bj) {
        this.min = min;
        this.max = max;
        this.bj = bj;
    }
    
    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public boolean isBj() {
        return bj;
    }

    public void setBj(boolean bj) {
        this.bj = bj;
    }
    
    
    
}
