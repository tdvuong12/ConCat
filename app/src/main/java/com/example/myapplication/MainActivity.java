 package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    // Hello

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
}