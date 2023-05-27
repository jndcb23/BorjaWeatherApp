package com.example.borjaweatherapp.ui.customdialog;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.widget.RelativeLayout;

public class CustomProgressBarController {

    Context context;
    CustomProgressBar customProgressBar;

    public CustomProgressBarController(Context context, CustomProgressBar customProgressBar) {
        this.context = context;
        this.customProgressBar = customProgressBar;
        createProgressBar();
    }

    private void createProgressBar() {
        RelativeLayout layout = new RelativeLayout(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        customProgressBar.setIndeterminate(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            customProgressBar.setForegroundGravity(Gravity.CENTER);
        }
        layout.addView(customProgressBar, params);
    }
}
