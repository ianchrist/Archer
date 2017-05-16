package com.example.chriia01.iansarcher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

public class PlayerOne {

    RectF rect;

    // Player's ship
    private Bitmap bitmap;

    // Ship size
    private float length;
    private float height;

    // Left side of ship location
    private float x;

    // Top of ship location
    private float y;

    // How fast is the ship in pixels per second
    private float shipSpeed;

    // The direction of the ship
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;

    // Ship's movement state
    private int shipMoving = STOPPED;

    //Constructor
    public PlayerOne(Context context, int screenX, int screenY){

        // New hitbox
        rect = new RectF();

        //Size of ship
        length = screenX/10;
        height = screenY/10;

        // Center screen on bottom
        x = screenX / 2 - 58;
        y = screenY - 20;

        // Initialize the bitmap
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.playership);

        // Changes bitmap's size according to the resolution
        bitmap = Bitmap.createScaledBitmap(bitmap,
                (int) (length),
                (int) (height),
                false);

        // How fast is the player in pixels per second
        shipSpeed = 700;
    }


    public RectF getRect(){
        return rect;
    }

    //Returns the ship's bitmap
    public Bitmap getBitmap(){
        return bitmap;
    }

    // Returns ship's left point
    public float getX(){
        return x;
    }

    // Returns ship's length
    public float getLength(){
        return length;
    }

    // Change direction
    public void setMovementState(int state){
        shipMoving = state;
    }

    //Helps move the ship
    public void update(long fps){
        if(shipMoving == LEFT){
            x = x - shipSpeed / fps;
        }

        if(shipMoving == RIGHT){
            x = x + shipSpeed / fps;
        }

        // Update the hitbox
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + length;

    }
}
