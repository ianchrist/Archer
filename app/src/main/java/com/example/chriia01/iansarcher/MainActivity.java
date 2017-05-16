package com.example.chriia01.iansarcher;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;


public class MainActivity extends Activity {
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get size of screen
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Create game view
        gameView = new GameView(this, size.x, size.y);
        setContentView(gameView);

    }

    // Resumes game
    @Override
    protected void onResume() {
        super.onResume();

        gameView.resume();
    }

    // Pauses the game
    @Override
    protected void onPause() {
        super.onPause();

        gameView.pause();
    }
}