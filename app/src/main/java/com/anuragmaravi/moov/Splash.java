package com.anuragmaravi.moov;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by anuragmaravi on 12/04/16.
 */
public class Splash extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        final ImageView iv = (ImageView) findViewById(R.id.imageView);
        Animation an = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        final Animation an2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_out);
        iv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                iv.startAnimation(an2);
                Splash.this.finish();
                Splash.this.startActivity(new Intent(Splash.this.getBaseContext(), MainActivity.class));
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });

        Typeface typeface_light = Typeface.createFromAsset(getAssets(),"Roboto-Light.ttf");

        TextView textView3 = (TextView) findViewById(R.id.textView_splash);

        textView3.setTypeface(typeface_light);
    }
}
