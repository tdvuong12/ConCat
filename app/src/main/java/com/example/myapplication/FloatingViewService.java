package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class FloatingViewService extends Service
        implements View.OnClickListener, View.OnDragListener {

    /**
     * mWindowManager: System service responsible for managing what is displayed
     * and organized on the screen of the user.
     * mFloatingView: Design, action, and algorithms relating to the floating widget
     * collapsedView:
     * expandedView:
     vtd12345678910111231111111111111*/


    private WindowManager mWindowManager;
    private View mFloatingView;
    private View collapsedView;
    private View expandedView;

    private int mWidth = 0;

    @Override
    public boolean onDrag(View v, DragEvent event) {
        return false;
    }

    public FloatingViewService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mFloatingView == null) {
            mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null);
            final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT
            );
            mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            mWindowManager.addView(mFloatingView, params);

            // getting the collapsed and expanded view
            collapsedView = mFloatingView.findViewById(R.id.layoutCollapsed);
            expandedView = mFloatingView.findViewById(R.id.layoutExpanded);

            //adding click listener to close button and expanded view
            mFloatingView.findViewById(R.id.buttonClose).setOnClickListener(this);
            expandedView.setOnClickListener(this);

            Display display = mWindowManager.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            /*
            final RelativeLayout relativeLayout = mFloatingView.findViewById(R.id.layout);
            ViewTreeObserver viewTreeObserver = relativeLayout.getViewTreeObserver();
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    relativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int width = relativeLayout.getMeasuredWidth();
                    mWidth = size.x - width;
                }
            });

             */

            // adding touch listener for drag movement
            WidgetMovement widgetMovement = new WidgetMovement();
            widgetMovement.setParams(mWindowManager, mFloatingView, params);
            widgetMovement.setViews(collapsedView, expandedView);
            mFloatingView.findViewById(R.id.relativeLayoutParent).setOnTouchListener(new View.OnTouchListener() {
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;
                private static final int CLICK_THRESHOLD = 200;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
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
                            int xDiff = Math.round(event.getRawX() - initialTouchX);
                            int yDiff = Math.round(event.getRawY() - initialTouchY);
                            params.x = initialX + (int) xDiff;
                            params.y = initialY + (int) yDiff;
                            Log.i("", params.x + " and " + params.y);
                            Log.i("", xDiff + " and " + yDiff);
                            mWindowManager.updateViewLayout(mFloatingView, params);
                            return true;
                        case MotionEvent.ACTION_UP:
                            float closestWall = params.x >= 0 ? maxX : minX;
                            params.x = (int) closestWall;
                            mWindowManager.updateViewLayout(mFloatingView, params);
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
                        default:
                            return false;
                    }
                }
            });
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layoutExpanded:
                collapsedView.setVisibility(View.VISIBLE);
                expandedView.setVisibility(View.GONE);
                Log.i("", "Button layout closed");
                break;
            case R.id.buttonClose:
                stopSelf();
                break;
            default:
        }
    }

    public void onDestroy(){
        super.onDestroy();
        if (mFloatingView != null) {
            mWindowManager.removeView(mFloatingView);
        }
    }

}
