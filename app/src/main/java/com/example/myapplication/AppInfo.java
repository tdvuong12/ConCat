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
    private ApplicationInfo applicationInfo;
    private PackageManager pm;

    public AppInfo(Context c, ApplicationInfo applicationInfo, PackageManager pm) {
        this.c = c;
        this.applicationInfo = applicationInfo;
        this.pm = pm;
        this.name = applicationInfo.packageName;
    }
    public Drawable getIcon() throws PackageManager.NameNotFoundException {
        return pm.getApplicationIcon(this.name);
    }

    public String getLabel() {
        return (String) pm.getApplicationLabel(applicationInfo);
    }

    public void launch() {
        try {
            c.startActivity(pm.getLaunchIntentForPackage(this.name));
        } catch (ActivityNotFoundException err) {
            Toast t = Toast.makeText(c,
                    R.string.app_not_found, Toast.LENGTH_SHORT);
            t.show();
        }
    }
}
