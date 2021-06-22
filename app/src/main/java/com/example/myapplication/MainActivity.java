 package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.stream.Collectors;


import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button firstBtn = findViewById(R.id.firstButton);
        firstBtn.setOnClickListener(this);
        Button secondBtn = findViewById(R.id.secondButton);
        secondBtn.setOnClickListener(this);
        Button thirdBtn = findViewById(R.id.thirdButton);
        thirdBtn.setOnClickListener(this);
        Button fourthBtn = findViewById(R.id.fourthButton);
        fourthBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TextView display = findViewById(R.id.display);
        switch (v.getId()) {
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

    //    private void launchApp(ApplicationInfo packagesInfo) {
//        Intent mIntent = getPackageManager().getLaunchIntentForPackage(packagesInfo.packageName);
//        if (mIntent != null) {
//            try {
//                startActivity(mIntent);
//            } catch (ActivityNotFoundException err) {
//                Toast t = Toast.makeText(getApplicationContext(),
//                        R.string.app_not_found, Toast.LENGTH_SHORT);
//                t.show();
//            }
//        } else {
//            Log.d(TAG, "nope");
//        }
//    }
}