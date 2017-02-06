package com.jikexueyuan.screenlocker;

import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by huyiqing on 2017/1/23.
 */

public class OneKeyLockActivity extends AppCompatActivity {
    private DevicePolicyManager devicePolicyManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
//        使用try catch判断是否绑定管理员权限若未开启跳转到注册界面
        try {
            devicePolicyManager.lockNow();
            finish();
        }catch (Exception e) {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
