package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
        holder.title.setText(appInfos.get(position).getName());
        holder.icon.setImageDrawable(appInfos.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return appInfos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView icon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView);
            icon = itemView.findViewById(R.id.imageView);
        }
    }
}
