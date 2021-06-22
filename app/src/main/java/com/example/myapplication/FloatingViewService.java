package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

public class FloatingViewService extends Service
        implements View.OnClickListener, View.OnDragListener {

    /**
     * mWindowManager: System service responsible for managing what is displayed
     * and organized on the screen of the user.
     * mFloatingView: Design, action, and algorithms relating to the floating widget
     * collapsedView:
     * expandedView:
     */
    private WindowManager mWindowManager;
    private View mFloatingView;
    private View collapsedView;
    private View expandedView;

    @Override
    public boolean onDrag(View v, DragEvent event) {
        return false;
    }

    public FloatingViewService() {

    }

    public void onCreate(){
        super.onCreate();

        /** Getting the widget layout from xml using layout inflater
         * param refers to the layout setting that the window manager is supposed to
         * cross apart.
         * First two parameters refer to how much should the window envelop the content.
         * Here, the window is shrink so that it can wrap the content enough but not fill the screen
         * May need to change the parameter and flag name so that the layout allows
         * applications to be launched.
         */
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        // getting window services and add the floating view to it
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);

        // getting the collapsed and expanded view
        collapsedView = mFloatingView.findViewById(R.id.layoutCollapsed);
        expandedView = mFloatingView.findViewById(R.id.layoutExpanded);

        //adding click listener to close button and expanded view
        mFloatingView.findViewById(R.id.buttonClose).setOnClickListener(this);
        expandedView.setOnClickListener(this);

        // adding touch listener for drag movement
        WidgetMovement widgetMovement = new WidgetMovement();
        widgetMovement.setParams(mWindowManager, mFloatingView, params);
        widgetMovement.setViews(collapsedView, expandedView);
        mFloatingView.findViewById(R.id.relativeLayoutParent).
                setOnTouchListener(widgetMovement);
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
