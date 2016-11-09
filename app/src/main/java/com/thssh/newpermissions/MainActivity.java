package com.thssh.newpermissions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class MainActivity extends BaseActivity {
    private FloatingActionButton fabRecord, fabSave, fabCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fabRecord = (FloatingActionButton) findViewById(R.id.fab_record);
        fabSave = (FloatingActionButton) findViewById(R.id.fab_sd);
        fabCall = (FloatingActionButton) findViewById(R.id.fab_call);
        fabRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordSounds();
            }
        });
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewFile();
            }
        });
        fabCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhone();
            }
        });
    }

    /* 基础实现拨打电话*/
    private void callPhone() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CALL_PHONE
            }, Constants.CALL_PHONE);
        }else{
            doCallPhone();
        }
    }

    private void doCallPhone() {
        Snackbar.make(fabCall, "拨打电话具体实现", Snackbar.LENGTH_SHORT).show();
    }

    /* 封装实现操作文件 */
    private void createNewFile() {
        requestPermission(Constants.WRITE_EXTERNAL_CODE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void onWriteExternalDenied() {
        Snackbar.make(fabSave, "获取文件权限失败", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onWriteExternalGranted() {
        doCreateFile();
    }

    private void doCreateFile() {
        Snackbar.make(fabSave, "文件操作具体实现", Snackbar.LENGTH_SHORT).show();
    }

    /* 注解库实现录音 */
    private void recordSounds() {
        PermissionGen.needPermission(this, Constants.RECORD_AUDIO, Manifest.permission.RECORD_AUDIO);
    }

    @PermissionSuccess(requestCode = Constants.RECORD_AUDIO)
    public void doRecordSounds(){
        Snackbar.make(fabRecord, "录音中...", Snackbar.LENGTH_SHORT).show();
    }

    @PermissionFail(requestCode = Constants.RECORD_AUDIO)
    public void onPermissionFailed(){
        Snackbar.make(fabRecord, "没有录音权限", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        switch (requestCode){
            case Constants.CALL_PHONE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    doCallPhone();
                }else{
                    // 请求被拒绝，提示用户
                    Snackbar.make(fabCall, "拨打电话权限被拒绝", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
