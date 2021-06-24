 package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

 public class MainActivity extends AppCompatActivity implements View.OnLongClickListener {

    Button[] buttonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AppInfo emptyApp = AppInfo.none(this);

        Button firstBtn = findViewById(R.id.firstButton);
        Button secondBtn = findViewById(R.id.secondButton);
        Button thirdBtn = findViewById(R.id.thirdButton);
        Button fourthBtn = findViewById(R.id.fourthButton);

        buttonList = new Button[] { firstBtn, secondBtn, thirdBtn, fourthBtn };

        for (Button btn: buttonList) {
            emptyApp.setButton(btn);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}