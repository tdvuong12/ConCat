package com.example.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

public class AppInfo {
    private Context c;
    private String name;
    private Drawable icon;
    private Intent intent;

    public AppInfo(Context c, ApplicationInfo applicationInfo, PackageManager pm) {
        this.c = c;
        this.name = applicationInfo.packageName;
        try {
            this.icon = pm.getApplicationIcon(this.name);
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        this.intent = pm.getLaunchIntentForPackage(this.name);
    }

    public String getName() {
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void launch() {
        try {
            c.startActivity(intent);
        } catch (ActivityNotFoundException err) {
            Toast t = Toast.makeText(c,
                    R.string.app_not_found, Toast.LENGTH_SHORT);
            t.show();
        }
    }
}
