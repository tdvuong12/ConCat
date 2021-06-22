package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import com.example.myapplication.databinding.ActivityScrollingBinding;

import static android.content.ContentValues.TAG;

public class ScrollingActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private ActivityScrollingBinding binding;
    private ActionBar actionBar;

    ListView applicationListView;
    String[] applicationNames = { "Google", "Facebook", "Google", "Facebook", "Google", "Facebook", "Google", "Facebook",
            "Google", "Facebook", "Google", "Facebook"};
    int[] applicationIcons = { R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScrollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());
        actionBar = getSupportActionBar();
        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(this);

        applicationListView = findViewById(R.id.applicationListView);

        ApplicationAdapter adapter = new ApplicationAdapter(this, applicationNames, applicationIcons);

        applicationListView.setAdapter(adapter);
        applicationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(ScrollingActivity.this, "Facebook", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(ScrollingActivity.this, "Google", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        if (offset == 0) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        else {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
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