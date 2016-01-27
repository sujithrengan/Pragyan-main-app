package com.delta.pragyan16;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends Activity {

    ImageView images[] = new ImageView[4];
    Animation animations[] = new Animation[4];
    ImageView bigLogo;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnimations();
            }
        },1000);
//        startAnimations();
    }

    private void startAnimations() {
//        final ImageView image = (ImageView) findViewById(R.id.logo_app);
//        Animation animation = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.zoom_chakra_in);
//        final ImageView image2 = (ImageView) findViewById(R.id.logo_app_second);
//        final Animation animation2 = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.zoom_chakra_out_left);
//        image.startAnimation(animation);
//        image2.startAnimation(animation2);

        bigLogo = (ImageView) findViewById(R.id.pragyan_logo_large);
        for (i = 0; i < 4; i++) {
            images[i] = (ImageView) findViewById(getResources().getIdentifier("logo_app_" + i, "id", "com.delta.pragyan16"));
            animations[i] = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.zoom_chakra_in);
            images[i].startAnimation(animations[i]);
            animations[i].setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    for (int i = 0; i < 4; i++) {
                        if (i % 2 == 0)
                            animations[i] = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.zoom_chakra_out_left);
                        else
                            animations[i] = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.zoom_chakra_out_right);
                        images[i].startAnimation(animations[i]);
                        animations[i].setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                for (int i = 0; i < 4; i++)
                                    images[i].setVisibility(View.INVISIBLE);
                                bigLogo.setVisibility(View.VISIBLE);
                                animations[0] = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.pragyan_logo_bounce);
                                animations[0].setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                SplashScreen.this.startActivity(new Intent(SplashScreen.this,MainActivity.class));
                                                SplashScreen.this.finish();
                                            }
                                        },1000);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                                bigLogo.setAnimation(animations[0]);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

    }

}
