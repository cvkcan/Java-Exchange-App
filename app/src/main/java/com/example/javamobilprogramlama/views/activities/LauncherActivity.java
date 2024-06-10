package com.example.javamobilprogramlama.views.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.javamobilprogramlama.R;
import com.example.javamobilprogramlama.databinding.ActivityLauncherBinding;
import com.google.android.material.snackbar.Snackbar;

public class LauncherActivity extends AppCompatActivity {
    private ActivityLauncherBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLauncherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splashanimation);
        binding.traceImage.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                checkInternetConnection();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Handle animation end
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Handle animation repeat
            }
        });
    }

     private void checkInternetConnection() {
         ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

         if (connectivityManager.getActiveNetwork() == null) {
             Snackbar.make(binding.getRoot(), R.string.internetException, Snackbar.LENGTH_INDEFINITE)
                     .setAction(R.string.exception, new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             recreate();
                         }
                     })
                     .show();
         } else {
             new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     startActivity(new Intent(LauncherActivity.this, HomepageActivity.class));
                     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                         overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, 0, 0);
                     }
                     finish();
                 }
             }, 2000);
         }
     }
}
