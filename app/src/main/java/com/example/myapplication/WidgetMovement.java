package com.example.myapplication;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class WidgetMovement extends Activity implements View.OnTouchListener {
    private int initialX;
    private int initialY;
    private float initialTouchX;
    private float initialTouchY;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams params;
    private View mOverlayView;
    private View collapsedView;
    private View expandedView;
    private PackageManager packageManager;


    private int CLICK_THRESHOLD = 200;

    /**
     * Set initial params
     * @param mWindowManager
     * @param mOverlayView
     * @param params
     */
    public void setParams(WindowManager mWindowManager, View mOverlayView,
                          WindowManager.LayoutParams params){
        this.params = params;
        this.mWindowManager = mWindowManager;
        this.mOverlayView = mOverlayView;
    }

    public void setViews(View collapsedView, View expandedView){
        this.collapsedView = collapsedView;
        this.expandedView = expandedView;
    }

    public void setCoordinate() {

    }

    /**
     * Specify movements of the widget on the home screen
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Display display = mWindowManager.getDefaultDisplay();

        // get the 4 maximum coordinate value of the phone screen
        float maxX = (float) 0.5 * display.getWidth();
        float minX = -maxX;
        float maxY = (float) 0.5 * display.getHeight();
        float minY = -maxY;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = params.x;
                initialY = params.y;
                initialTouchX = event.getRawX();
                initialTouchY = event.getRawY();
                Log.i("", "Started action");
                Log.i("", initialX + "" + initialY);
                return true;
            case MotionEvent.ACTION_MOVE:
                float xDiff = Math.round(event.getRawX() - initialTouchX);
                float yDiff = Math.round(event.getRawY() - initialTouchY);
                params.x = initialX + (int) xDiff;
                params.y = initialY + (int) yDiff;
                Log.i("", params.x + " and " + params.y);
                Log.i("", xDiff + " and " + yDiff);
                mWindowManager.updateViewLayout(mOverlayView, params);
                return true;
            case MotionEvent.ACTION_UP:
                float closestWall = params.x >= 0 ? maxX : minX;
                params.x = (int) closestWall;
                mWindowManager.updateViewLayout(mOverlayView, params);
                if (event.getEventTime() - event.getDownTime() <= CLICK_THRESHOLD) {
                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            collapsedView.setVisibility(View.GONE);
                            expandedView.setVisibility(View.VISIBLE);
                        }
                    });
                    v.performClick();
                    Log.i("", "Button clicked");
                    return false;
                } else {
                    Log.i("", "Button moved");
                    return true;
                }
        }
        return false;
    }

    public void launchApp(ApplicationInfo appInfo) {
        Intent intent = getPackageManager().getLaunchIntentForPackage(appInfo.packageName);
        if (intent == null || appInfo == null) {
            return;
        }
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Activity not found!", Toast.LENGTH_SHORT).show();
        }
    }

    public void launchApp(String appInfo) {
        Intent intent = getPackageManager().getLaunchIntentForPackage(appInfo);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Activity not found!", Toast.LENGTH_SHORT).show();
        }
    }

}