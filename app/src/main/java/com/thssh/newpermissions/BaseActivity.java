package com.thssh.newpermissions;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by zhang on 2016/11/9.
 */
public class BaseActivity extends AppCompatActivity{
    public boolean hasPermission(String... permissions){
        for(String per : permissions){
            if(ContextCompat.checkSelfPermission(this, per)
                    != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    public void requestPermission(int code, String... permissions){
        if(hasPermission(permissions)){
            onWriteExternalGranted();
        }else {
            ActivityCompat.requestPermissions(this, permissions, code);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case Constants.WRITE_EXTERNAL_CODE:
                if(isAllGranted(grantResults)){
                    onWriteExternalGranted();
                }else{
                    onWriteExternalDenied();
                }
                break;
        }
    }

    private boolean isAllGranted(int[] grantResults) {
        for(int result : grantResults){
            if(result != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    protected void onWriteExternalDenied() {}

    protected void onWriteExternalGranted(){}
}
