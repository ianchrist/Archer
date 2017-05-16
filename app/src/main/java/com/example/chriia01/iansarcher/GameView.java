package com.example.chriia01.iansarcher;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    private Context context;

    // The thread the game is running on
    private Thread gameThread = null;

    // Lock surface to draw
    private SurfaceHolder ourHolder;

    // Tells if game is playing
    private volatile boolean playing;

    // Game paused
    private boolean paused = true;

    // Used to draw pretty things
    private Canvas canvas;
    private Paint paint;

    // Keeps track of frames per second, used for animation
    private long fps;

    // Used to help calculate the fps
    private long timeThisFrame;

    // The size of the screen in pixels
    private int screenX;
    private int screenY;

    // The player's ship
    private PlayerOne playerOne;
    private PlayerTwo playerTwo;

    // The player's bullets
    private Bullet bulletOne;
    private Bullet bulletTwo;
    private Bullet bulletOne2;
    private Bullet bulletTwo2;
    private Bullet bulletOne3;
    private Bullet bulletTwo3;
    private Bullet bulletOne4;
    private Bullet bulletTwo4;
    private Bullet bulletOne5;
    private Bullet bulletTwo5;

    //Says who won
    boolean p1Victory = false;
    boolean p2Victory = false;

    //Game over?
    boolean done = false;

    //Construtor
    public GameView(Context context, int x, int y) {
        super(context);
        this.context = context;

        ourHolder = getHolder();
        paint = new Paint();

        paint.setTextSize(100);

        screenX = x;
        screenY = y;

        prepareLevel();
    }

    //We prepare the map to begin a new game
    private void prepareLevel(){
        // Make a new player space ship
        playerOne = new PlayerOne(context, screenX, screenY);
        playerTwo = new PlayerTwo(context, screenX, screenY);

        // Prepare the players bullet
        bulletOne = new Bullet(screenY);
        bulletTwo = new Bullet(screenY);
        bulletOne2 = new Bullet(screenY);
        bulletTwo2 = new Bullet(screenY);
        bulletOne3 = new Bullet(screenY);
        bulletTwo3 = new Bullet(screenY);
        bulletOne4 = new Bullet(screenY);
        bulletTwo4 = new Bullet(screenY);
        bulletOne5 = new Bullet(screenY);
        bulletTwo5 = new Bullet(screenY);

    }

    //Runs the game
    @Override
    public void run() {
        while (playing) {

            long startFrameTime = System.currentTimeMillis();

            if(!paused){
                update();
            }

            draw();

            //FPS used for animations
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }



    }

    // Updates the game as it goes on
    private void update() {
        // Has the player's bullet hit the edge of the screen
        if (bulletOne.getImpactPointY() < -100) {
            bulletOne.setInactive();
            bulletOne.hideBullet();
        }

        if (bulletOne2.getImpactPointY() < -100) {
            bulletOne2.setInactive();
            bulletOne2.hideBullet();
        }

        if (bulletOne3.getImpactPointY() < -100) {
            bulletOne3.setInactive();
            bulletOne3.hideBullet();
        }

        if (bulletOne4.getImpactPointY() < -100) {
            bulletOne4.setInactive();
            bulletOne4.hideBullet();
        }

        if (bulletOne5.getImpactPointY() < -100) {
            bulletOne5.setInactive();
            bulletOne5.hideBullet();
        }

        if (bulletTwo.getImpactPointY() > screenY + 100) {
            bulletTwo.setInactive();
            bulletTwo.hideBullet();
        }

        if (bulletTwo2.getImpactPointY() > screenY + 100) {
            bulletTwo2.setInactive();
            bulletTwo2.hideBullet();
        }

        if (bulletTwo3.getImpactPointY() > screenY + 100) {
            bulletTwo3.setInactive();
            bulletTwo3.hideBullet();
        }

        if (bulletTwo4.getImpactPointY() > screenY + 100) {
            bulletTwo4.setInactive();
            bulletTwo4.hideBullet();
        }

        if (bulletTwo5.getImpactPointY() > screenY + 100) {
            bulletTwo5.setInactive();
            bulletTwo5.hideBullet();
        }

        // Move the player's ship
        playerOne.update(fps);
        playerTwo.update(fps);

        // Update the player's bullets
        if (bulletOne.getStatus()) {
            bulletOne.update(fps);
        }

        if (bulletTwo.getStatus()){
            bulletTwo.update(fps);
        }

        if (bulletOne2.getStatus()){
            bulletOne2.update(fps);
        }

        if (bulletTwo2.getStatus()){
            bulletTwo2.update(fps);
        }

        if (bulletOne3.getStatus()){
            bulletOne3.update(fps);
        }

        if (bulletTwo3.getStatus()){
            bulletTwo3.update(fps);
        }

        if (bulletOne4.getStatus()){
            bulletOne4.update(fps);
        }

        if (bulletTwo4.getStatus()){
            bulletTwo4.update(fps);
        }

        if (bulletOne5.getStatus()){
            bulletOne5.update(fps);
        }

        if (bulletTwo5.getStatus()){
            bulletTwo5.update(fps);
        }

        //Has P1 hit P2?
        if(bulletOne.getStatus() || bulletOne2.getStatus() || bulletOne3.getStatus() || bulletOne4.getStatus() || bulletOne5.getStatus()){
            if (RectF.intersects(bulletOne.getRect(), playerTwo.getRect()) || RectF.intersects(bulletOne2.getRect(), playerTwo.getRect()) || RectF.intersects(bulletOne3.getRect(), playerTwo.getRect()) || RectF.intersects(bulletOne4.getRect(), playerTwo.getRect()) || RectF.intersects(bulletOne5.getRect(), playerTwo.getRect())){
                p1Victory = true;
            }
        }

        //Has P2 hit P1?
        if(bulletTwo.getStatus() || bulletTwo2.getStatus() || bulletTwo3.getStatus() || bulletTwo4.getStatus() || bulletTwo5.getStatus()){
            if (RectF.intersects(bulletTwo.getRect(), playerOne.getRect()) || RectF.intersects(bulletTwo2.getRect(), playerOne.getRect()) || RectF.intersects(bulletTwo3.getRect(), playerOne.getRect()) || RectF.intersects(bulletTwo4.getRect(), playerOne.getRect()) || RectF.intersects(bulletTwo5.getRect(), playerOne.getRect())){
                p2Victory = true;
            }
        }

        //Game over
        if (done) {
            done = false;
            prepareLevel();
        }
    }

    //Draws all the images
    private void draw(){
        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();

            paint.setColor(Color.rgb(255,255,255));
            // Draw the background color based on who wins, or if it is still going
            if (p1Victory){
                canvas.drawColor(Color.rgb(0,0,255));
                canvas.drawText("You win!", screenX/2 - 185, screenY/2, paint);
            }

            else if (p2Victory){
                canvas.drawColor(Color.rgb(255,0,0));
                canvas.save();
                canvas.rotate(180,screenX/2, screenY/2);
                canvas.drawText("You win!", screenX/2 - 185, screenY/2, paint);
                canvas.restore();
            }

            else {
                canvas.drawColor(Color.rgb(0, 0, 0));
            }

            if (!p1Victory && !p2Victory) {
                paint.setColor(Color.rgb(70, 70, 70));
                canvas.drawLine(0, screenY - screenY / 8, screenX, screenY - screenY / 8, paint);
                canvas.drawLine(0, screenY / 8 - 50, screenX, screenY / 8 - 50, paint);
            }

            // Choose the brush color for drawing
            paint.setColor(Color.rgb(255, 255,255));

            //Draw players
            canvas.drawBitmap(playerOne.getBitmap(), playerOne.getX(), screenY -175, paint);
            canvas.drawBitmap(playerTwo.getBitmap(), playerTwo.getX(), -60, paint);

            // Draw the players bullet if it is active
            if(bulletOne.getStatus()){
                canvas.drawRect(bulletOne.getRect(), paint);
            }

            if (bulletTwo.getStatus()){
                canvas.drawRect(bulletTwo.getRect(), paint);
            }

            if (bulletOne2.getStatus()){
                canvas.drawRect(bulletOne2.getRect(), paint);
            }

            if (bulletTwo2.getStatus()){
                canvas.drawRect(bulletTwo2.getRect(), paint);
            }

            if (bulletOne3.getStatus()){
                canvas.drawRect(bulletOne3.getRect(), paint);
            }

            if (bulletTwo3.getStatus()){
                canvas.drawRect(bulletTwo3.getRect(), paint);
            }

            if (bulletOne4.getStatus()){
                canvas.drawRect(bulletOne4.getRect(), paint);
            }

            if (bulletTwo4.getStatus()){
                canvas.drawRect(bulletTwo4.getRect(), paint);
            }

            if (bulletOne5.getStatus()){
                canvas.drawRect(bulletOne5.getRect(), paint);
            }

            if (bulletTwo5.getStatus()){
                canvas.drawRect(bulletTwo5.getRect(), paint);
            }

            //Post our drawing
            ourHolder.unlockCanvasAndPost(canvas);

            //Pauses the game to show p1 wins, with the different background
            if (p1Victory) {
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException ex){
                    Thread.currentThread().interrupt();
                }
                p1Victory = false;
                done = true;
            }

            //Pauses the game to show p2 wins, with the different background
            else if (p2Victory) {
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException ex){
                    Thread.currentThread().interrupt();
                }
                p2Victory = false;
                done = true;
            }
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "pausing game");
        }

    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    //Helps the game react to touch
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        int pointerIndex = motionEvent.getActionIndex();
        int pointerId = motionEvent.getPointerId(pointerIndex);
        int maskedAction = motionEvent.getActionMasked();

        switch (maskedAction) {
            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                paused = false;

                if(motionEvent.getY(pointerIndex) > screenY - screenY / 8) { //P1 move
                    if (motionEvent.getX(pointerIndex) > screenX / 2) {
                        playerOne.setMovementState(playerOne.RIGHT);
                    } else {
                        playerOne.setMovementState(playerOne.LEFT);
                    }


                }

                if (motionEvent.getY(pointerIndex) < screenY / 8 - 50) { //p2 move
                    if (motionEvent.getX(pointerIndex) < screenX / 2) {
                        playerTwo.setMovementState(playerTwo.LEFT);
                    }
                    else {
                        playerTwo.setMovementState(playerTwo.RIGHT);
                    }
                }

                if(motionEvent.getY(pointerIndex) < screenY - screenY / 8 && motionEvent.getY(pointerIndex) > screenY/2) { //P1 shoot
                    // Shots fired
                    if (!bulletOne.getStatus()) {
                        bulletOne.shoot(playerOne.getX() + playerOne.getLength() / 2, screenY, bulletOne.UP);
                    }

                    else if (!bulletOne2.getStatus()) {
                        bulletOne2.shoot(playerOne.getX() + playerOne.getLength() / 2, screenY, bulletOne2.UP);
                    }

                    else if (!bulletOne3.getStatus()) {
                        bulletOne3.shoot(playerOne.getX() + playerOne.getLength() / 2, screenY, bulletOne3.UP);
                    }

                    else if (!bulletOne4.getStatus()) {
                        bulletOne4.shoot(playerOne.getX() + playerOne.getLength() / 2, screenY, bulletOne4.UP);
                    }

                    else if (!bulletOne5.getStatus()) {
                        bulletOne5.shoot(playerOne.getX() + playerOne.getLength() / 2, screenY, bulletOne5.UP);
                    }
                }

                if(motionEvent.getY(pointerIndex) > screenY / 8 - 50 && motionEvent.getY(pointerIndex) < screenY/2) {
                    if (!bulletTwo.getStatus()) {
                        bulletTwo.shoot(playerTwo.getX() + playerTwo.getLength() / 2, 0, bulletTwo.DOWN);
                    }

                    else if (!bulletTwo2.getStatus()) {
                        bulletTwo2.shoot(playerTwo.getX() + playerTwo.getLength() / 2, 0, bulletTwo2.DOWN);
                    }

                    else if (!bulletTwo3.getStatus()) {
                        bulletTwo3.shoot(playerTwo.getX() + playerTwo.getLength() / 2, 0, bulletTwo3.DOWN);
                    }

                    else if (!bulletTwo4.getStatus()) {
                        bulletTwo4.shoot(playerTwo.getX() + playerTwo.getLength() / 2, 0, bulletTwo4.DOWN);
                    }

                    else if (!bulletTwo5.getStatus()) {
                        bulletTwo5.shoot(playerTwo.getX() + playerTwo.getLength() / 2, 0, bulletTwo5.DOWN);
                    }
                }



                break;


            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if(motionEvent.getY(pointerIndex) > screenY - screenY / 8) {
                    playerOne.setMovementState(playerOne.STOPPED);
                }

                if(motionEvent.getY(pointerIndex) < screenY / 8){
                    playerTwo.setMovementState(playerTwo.STOPPED);
                }

                break;
        }
        return true;
    }
}


