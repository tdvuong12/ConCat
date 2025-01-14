package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private List<AppInfo> appInfos;

    public MyAdapter(Context c, List<AppInfo> appInfos) {
        this.context = c;
        this.appInfos = appInfos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        AppInfo app = appInfos.get(position);
        try {
            holder.title.setText(app.getLabel(this.context));
            holder.icon.setImageDrawable(app.getIcon(this.context));
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, e.toString());
        }

        holder.rowLayout.setOnClickListener(v -> {
            AppCompatActivity c = (AppCompatActivity) context;
            Intent intent = new Intent(c, MainActivity.class);

            int buttonID = c.getIntent().getIntExtra("Button ID", 0);

            String applabel = "";

            try {
                applabel = app.getLabel(context);
            } catch (PackageManager.NameNotFoundException e) {
                Log.d(TAG, e.toString());
            }

            //Setup the check function
            AlertDialog.Builder alertdialog = new AlertDialog.Builder(context);
            alertdialog.setTitle(applabel);
            alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    intent.putExtra("app", app);
                    intent.putExtra("Button ID", buttonID);
                    c.startActivity(intent);
                }
            });
            alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertdialog.show();








        });
    }

    /*public void Check(String label, Context context, Boolean sure){
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(context);
        alertdialog.setTitle(label);
        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sure = true;
            }
        });
        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sure = false;
            }
        });
        alertdialog.show();
    }

     */





    @Override
    public int getItemCount() {
        return appInfos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView icon;
        LinearLayout rowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView);
            icon = itemView.findViewById(R.id.imageView);
            rowLayout = itemView.findViewById(R.id.rowLayout);
        }
    }
}
