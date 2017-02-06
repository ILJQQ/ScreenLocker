package com.jikexueyuan.screenlocker;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.sql.SQLOutput;

/**
 * Created by huyiqing on 2017/1/23.
 */

public class DeviceManagerBR extends DeviceAdminReceiver {

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        Toast.makeText(context,"已经成功注册组件",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        Toast.makeText(context,"已经注销组件",Toast.LENGTH_SHORT).show();
    }
}
