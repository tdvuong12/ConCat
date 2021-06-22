package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class List extends AppCompatActivity {

    ListView applicationListView;
    String[] applicationNames = {"Facebook", "Google"};
    int[] applicationIcons = { R.mipmap.ic_launcher, R.mipmap.ic_launcher };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "start List");
        setContentView(R.layout.activity_scrolling);

        applicationListView = findViewById(R.id.applicationListView);

        ApplicationAdapter adapter = new ApplicationAdapter(this, applicationNames, applicationIcons);

        applicationListView.setAdapter(adapter);
        applicationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(List.this, "Facebook", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(List.this, "Google", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class ApplicationAdapter extends ArrayAdapter<String> {
        private Context context;
        private String[] names;
        private int[] icons;

        ApplicationAdapter (Context c, String[] names, int[] icons){
            super(c, R.layout.row, R.id.title, names);
            this.context = c;
            this.names = names;
            this.icons = icons;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView icon = row.findViewById(R.id.icon);
            TextView name = row.findViewById(R.id.title);

            icon.setImageResource(icons[position]);
            name.setText(names[position]);

            return row;
        }
    }


}