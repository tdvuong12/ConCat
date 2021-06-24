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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private static final int WINDOW_PERMISSION = 123;
    private static boolean PERMISSION_GRANTED = true;

    private ApplicationInfo[] appList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button widget = findViewById(R.id.ConCat);
        widget.setOnClickListener(this);
        Button firstBtn = findViewById(R.id.firstButton);
        firstBtn.setOnClickListener(this);
        Button secondBtn = findViewById(R.id.secondButton);
        secondBtn.setOnClickListener(this);
        Button thirdBtn = findViewById(R.id.thirdButton);
        thirdBtn.setOnClickListener(this);
        Button fourthBtn = findViewById(R.id.fourthButton);
        fourthBtn.setOnClickListener(this);
    }

    private void askForSystemOverlayPermission(){
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
            case R.id.firstButton:
                // launchApp(filteredPackages.get(4));
                break;
            case R.id.secondButton:
                // Toast.makeText(this, "firstClicked", Toast.LENGTH_SHORT).show();
                display.setText("secondClicked");
                startActivity(new Intent(this, ScrollingActivity.class));
                break;
            case R.id.thirdButton:
                // Toast.makeText(this, "firstClicked", Toast.LENGTH_SHORT).show();
                display.setText("thirdClicked");
                break;
            case R.id.fourthButton:
                // Toast.makeText(this, "firstClicked", Toast.LENGTH_SHORT).show();
                display.setText("fourthClicked");
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