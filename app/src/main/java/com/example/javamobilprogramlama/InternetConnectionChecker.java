package com.example.javamobilprogramlama;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import javax.annotation.Nullable;

public class InternetConnectionChecker {
    private static View rootView;
    private static Activity activity;
    private static Fragment fragment;

    public static void initialize(View rootView, @Nullable Activity activity, @Nullable Fragment fragment) {
        InternetConnectionChecker.rootView = rootView;
        InternetConnectionChecker.activity = activity;
        InternetConnectionChecker.fragment = fragment;
    }

    public static boolean checkInternetConnection() {
        if (rootView == null) {
            return false;
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) rootView.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getActiveNetworkInfo() == null || !connectivityManager.getActiveNetworkInfo().isConnected()) {
            Snackbar.make(rootView, R.string.internetException, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.exception, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (activity != null) {
                                restartApp(activity);
                            }
                        }
                    })
                    .show();
            return false;
        }
        return true;
    }

    private static void restartApp(Activity activity) {
        Intent intent = activity.getPackageManager().getLaunchIntentForPackage(activity.getPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
            activity.finish();
            System.exit(0);
        }
    }
}
