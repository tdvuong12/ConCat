 package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

 public class MainActivity extends AppCompatActivity
         implements View.OnClickListener, View.OnLongClickListener {

    Button[] buttonList;

    private static final int WINDOW_PERMISSION = 123;
    private static boolean PERMISSION_GRANTED = true;

    // Hello

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AppInfo emptyApp = AppInfo.none(this);

        Button conCat = findViewById(R.id.ConCat);
        conCat.setOnClickListener(this);
        Button firstBtn = findViewById(R.id.firstButton);
        Button secondBtn = findViewById(R.id.secondButton);
        Button thirdBtn = findViewById(R.id.thirdButton);
        Button fourthBtn = findViewById(R.id.fourthButton);

        buttonList = new Button[]{firstBtn, secondBtn, thirdBtn, fourthBtn};

        for (Button btn : buttonList) {
            emptyApp.setButton(btn);
        }
    }


    private void askForSystemOverlayPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, WINDOW_PERMISSION);
        if (Settings.canDrawOverlays(this.getApplicationContext())) {
            this.PERMISSION_GRANTED = true;
        }
    }


    @Override
    public void onClick(View v) {
        TextView display = findViewById(R.id.display);
        switch (v.getId()) {
            case R.id.ConCat:
                if (PERMISSION_GRANTED == false) {
                    askForSystemOverlayPermission();
                    break;
                }
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(this)) {
                    startService(new Intent(MainActivity.this, FloatingViewService.class));
                } else {
                    askForSystemOverlayPermission();
                }
                break;
            default:
                break;
        }
    }

     @Override
     public boolean onLongClick(View v) {
         return false;
     }
 }