package com.mad.tapgame;

import android.content.Context;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import java.util.Random;

public class GameView extends View {
    Paint paint;
    int x, y, radius = 80;
    int score = 0;
    Random random;

    public GameView(Context context) {
        super(context);
        paint = new Paint();
        random = new Random();
        generateNewPosition();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        // Background
        canvas.drawColor(Color.WHITE);

        // Draw circle target
        paint.setColor(Color.RED);
        canvas.drawCircle(x, y, radius, paint);

        // Draw score
        paint.setColor(Color.BLACK);
        paint.setTextSize(60);
        canvas.drawText("Score: " + score, 50, 100, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            performClick();
            float touchX = event.getX();
            float touchY = event.getY();

            // Euclidean distance from touch to circle center
            double distance = Math.sqrt(
                    Math.pow(touchX - x, 2) +
                            Math.pow(touchY - y, 2)
            );

            if (distance < radius) {  // tap inside circle
                score++;
                generateNewPosition();
                invalidate();         // triggers onDraw()
            }
        }
        return true; // consume the event
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private void generateNewPosition() {
        x = random.nextInt(800) + 100;
        y = random.nextInt(1200) + 200;
    }
}