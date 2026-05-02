package com.mad.canvaanimation;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    AnimatedCircleView circleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleView = (AnimatedCircleView) findViewById(R.id.circleView);
        Button btnAnimate = (Button) findViewById(R.id.btnAnimate);

        btnAnimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });
    }

    private void startAnimation() {
        ValueAnimator radiusAnimator = ValueAnimator.ofFloat(50f, 150f);
        radiusAnimator.setDuration(1000);
        radiusAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleView.setRadius((float) animation.getAnimatedValue());
            }
        });

        ValueAnimator colorAnimator = ValueAnimator.ofObject(
                new ArgbEvaluator(), Color.BLUE, Color.RED
        );
        colorAnimator.setDuration(1000);
        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleView.setCircleColor((int) animation.getAnimatedValue());
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.playTogether(radiusAnimator, colorAnimator);
        set.start();
    }
}