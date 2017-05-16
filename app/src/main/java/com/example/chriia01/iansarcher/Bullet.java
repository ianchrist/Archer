package com.example.chriia01.iansarcher;

import android.graphics.RectF;

public class Bullet {

    //Location
    private float x;
    private float y;

    //The hitbox
    private RectF rect;

    // Which way it is moving
    public int UP = 0;
    public int DOWN = 1;

    // Direction and speed
    int heading = -1;
    float speed =  1400;

    // Size of bullet
    private int width = 10;
    private int height;

    //Tells if bullet is moving
    private boolean isActive;

    //Constructor
    public Bullet(int screenY) {

        height = screenY / 20;
        isActive = false;

        rect = new RectF();
    }

    // Make the bullet active and move
    public boolean shoot(float startX, float startY, int direction) {
        if (!isActive) {
            x = startX -5;
            y = startY;
            heading = direction;
            isActive = true;
            return true;
        }

        // Bullet already active
        return false;
    }

    //Updates the bullet
    public void update(long fps){

        // Just move up or down
        if(heading == UP){
            y = y - speed / fps;
        }else{
            y = y + speed / fps;
        }

        // Update rect
        rect.left = x;
        rect.right = x + width;
        rect.top = y;
        rect.bottom = y + height;

    }

    //Returns the hitbox
    public RectF getRect(){
        return  rect;
    }

    //Is it alive or dead?
    public boolean getStatus(){
        return isActive;
    }

    //Make bullet dead
    public void setInactive(){
        isActive = false;
    }

    //Make active
    public void setActive() { isActive = true; }

    //Hide bullet above screen. This fixes a bug where the bullets would
    //respawn on the bottom or top of the screen before they shoot
    public void hideBullet() {
        y = -200;
        rect.left = x;
        rect.right = x + width;
        rect.top = y;
        rect.bottom = y + height;
    }

    //Returns where the bullet is at
    public float getImpactPointY(){
        if (heading == DOWN){
            return y + height;
        }else{
            return  y;
        }

    }


}