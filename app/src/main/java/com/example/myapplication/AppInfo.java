package com.example.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public abstract class AppInfo {
    private Context c;

    public static final class SomeAppInfo extends AppInfo {
        private String name;
        private ApplicationInfo applicationInfo;
        private PackageManager pm;

        private SomeAppInfo(Context c, ApplicationInfo applicationInfo, PackageManager pm) {
            super.c = c;
            this.applicationInfo = applicationInfo;
            this.pm = pm;
            this.name = applicationInfo.packageName;
        }

        @Override
        public Drawable getIcon() throws PackageManager.NameNotFoundException {
            return pm.getApplicationIcon(this.name);
        }

        @Override
        public String getLabel() {
            return (String) pm.getApplicationLabel(applicationInfo);
        }

        private void launch() {
            try {
                super.c.startActivity(pm.getLaunchIntentForPackage(this.name));
            } catch (ActivityNotFoundException err) {
                Toast t = Toast.makeText(super.c,
                        R.string.app_not_found, Toast.LENGTH_SHORT);
                t.show();
            }
        }

        @Override
        public void setButton(Button btn) {
//            Log.d(TAG, "some app info");
//
//            Log.d(TAG, Boolean.toString(this.getLabel() == null));
//
//            btn.setText(this.getLabel());
//
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    SomeAppInfo.this.launch();
//                }
//            });
//
//            btn.setOnLongClickListener(v -> {
//                SomeAppInfo.super.c.startActivity(new Intent(SomeAppInfo.super.c, ScrollingActivity.class));
//                return false;
//            });

            btn.setOnClickListener(v -> {
                Toast.makeText(SomeAppInfo.super.c, "Empty Button! Hold to choose app", Toast.LENGTH_SHORT).show();
            });

            btn.setOnLongClickListener(v -> {
                Intent intent = new Intent(SomeAppInfo.super.c, ScrollingActivity.class);
                intent.putExtra("Button ID", btn.getId());
                SomeAppInfo.super.c.startActivity(intent);
                return false;
            });

            btn.setText("set");

        }
    }

    public static class EmptyApp extends AppInfo {
        private EmptyApp(Context c) {
            super.c = c;
        }

        @Override
        public Drawable getIcon() {
            return null;
        }

        @Override
        public String getLabel() {
            return null;
        }

        @Override
        public void setButton(Button btn) {
            btn.setOnClickListener(v -> {
                Toast.makeText(EmptyApp.super.c, "Empty Button! Hold to choose app", Toast.LENGTH_SHORT).show();
            });

            btn.setOnLongClickListener(v -> {
                Intent intent = new Intent(EmptyApp.super.c, ScrollingActivity.class);
                intent.putExtra("Button ID", btn.getId());
                EmptyApp.super.c.startActivity(intent);
                return false;
            });

            btn.setText("empty button");
        }
    }

    public static AppInfo none(Context c) {
        return new EmptyApp(c);
    }

    public static AppInfo some(Context c, ApplicationInfo applicationInfo, PackageManager pm) {
        return new SomeAppInfo(c, applicationInfo, pm);
    }

    public abstract Drawable getIcon() throws PackageManager.NameNotFoundException;

    public abstract String getLabel();

    public abstract void setButton(Button btn);
}
