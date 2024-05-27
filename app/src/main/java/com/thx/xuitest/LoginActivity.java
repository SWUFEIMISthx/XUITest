package com.thx.xuitest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private final Context mContext = this;
    private com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText ET_username;
    private com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText ET_password;
    public void handlePermission(){
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "已获得权限许可", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "正在请求权限", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        handlePermission();
        ET_username = findViewById(R.id.et_phone_number);
        ET_password = findViewById(R.id.et_pwd);
    }
    public void btn_register_onClick(View v){
        String username = ET_username.getText().toString();
        System.out.println(username);
        String password = ET_password.getText().toString();
        if(username.equals("") || password.equals("")){
            Toast.makeText(this, "还没有填写用户名和密码！", Toast.LENGTH_SHORT).show();
        }
        else if(password.length() < 10){
            Toast.makeText(this, "密码至少为10位！", Toast.LENGTH_SHORT).show();
        }
        else if(username.contains("\\") || username.contains("/") || username.contains(":") || username.contains("*")
                || username.contains("?") || username.contains("\"") || username.contains("<") || username.contains(">")
                || username.contains("|"))
        {
            Toast.makeText(this, "用户名中请勿包含\n \\ / : * ? \" < > |等特殊字符", Toast.LENGTH_SHORT).show();
        }
        else{
            Account temp = new Account(username, password, mContext);
            Intent intent;
            if(temp.saveAccount())
            {
                Toast.makeText(this, "注册成功! \n 为您直接登录", Toast.LENGTH_SHORT).show();
                intent = new Intent(LoginActivity.this, IndexActivity.class);
                intent.putExtra("userName", username);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "注册失败:( \n 该用户已存在，请尝试其他用户名", Toast.LENGTH_SHORT).show();
            }
        }
    }
}