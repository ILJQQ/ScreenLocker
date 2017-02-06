package com.jikexueyuan.screenlocker;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DevicePolicyManager devicePolicyManager;
    private Button btnRegister,btnUnRegister,btnLockScreen;
    private static final int DEVICE_POLICY_MANAGER_REQUEST_CODE = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnUnRegister = (Button) findViewById(R.id.btnUnRegister);
        btnLockScreen = (Button) findViewById(R.id.btnLockScreen);
        btnRegister.setOnClickListener(this);
        btnUnRegister.setOnClickListener(this);
        btnLockScreen.setOnClickListener(this);

//        跳转到获取到管理员权限后的activity
        if (devicePolicyManager.isAdminActive(new ComponentName(this, DeviceManagerBR.class))) {
            pageSetAdmin();
        }
    }

//    获取管理员权限后的界面
    private void pageSetAdmin() {
        btnRegister.setVisibility(View.INVISIBLE);
        btnUnRegister.setVisibility(View.VISIBLE);
        btnLockScreen.setVisibility(View.VISIBLE);
    }

//    未获取到管理员权限的界面
    private void PageLock() {
        btnRegister.setVisibility(View.VISIBLE);
        btnUnRegister.setVisibility(View.INVISIBLE);
        btnLockScreen.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLockScreen:
//                检测是否获取到权限因为未获取到权限锁屏按键并不显示所以这部分代码只是为了逻辑顺畅
                if (devicePolicyManager.getActiveAdmins() == null){
                    Toast.makeText(this,"请先注册系统组件权限",Toast.LENGTH_SHORT).show();
                }else {
                    devicePolicyManager.lockNow();
                }
                break;
            case R.id.btnRegister:
//                点击注册权限并返回顺利注册request code
                Intent i = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                i.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,new ComponentName(this,DeviceManagerBR.class));
                startActivityForResult(i,DEVICE_POLICY_MANAGER_REQUEST_CODE);
                break;
            case R.id.btnUnRegister:
//                注销管理员权限并设置界面为获取到权限后界面
                devicePolicyManager.removeActiveAdmin(new ComponentName(this,DeviceManagerBR.class));
                PageLock();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        根据结果返回码设置对应的界面
        switch (resultCode){
            case RESULT_OK:
                pageSetAdmin();
                break;
            case RESULT_CANCELED:
                PageLock();
                break;
        }
    }
}
