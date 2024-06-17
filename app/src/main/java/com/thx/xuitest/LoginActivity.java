package com.thx.xuitest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class LoginActivity extends AppCompatActivity {

    private final Context mContext = this;
    private com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText ET_username;
    private com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText ET_password;
    private com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText ET_verifyCode;
    private ImageView mImgVerifyCode;
    private CheckBox cbProtocol;
    private CheckBox cbRememberPassword;
    private static final String TAG = "LoginActivity";
    private String mCurrentVerifyCode;
    private SharedPreferences sharedPreferences;

    public void handlePermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "已获得权限许可", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "正在请求权限", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        handlePermission();

        sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);

        ET_username = findViewById(R.id.et_phone_number);
        ET_password = findViewById(R.id.et_pwd);
        ET_verifyCode = findViewById(R.id.et_verify_code);
        mImgVerifyCode = findViewById(R.id.img_verify_code);
        cbProtocol = findViewById(R.id.cb_protocol);
        cbRememberPassword = findViewById(R.id.cb_remember_password);

        ImageView logoImageView = findViewById(R.id.logo_image_view);
        Glide.with(this).load("https://images-special.oss-cn-chengdu.aliyuncs.com/Android_Images/logo_trans.png").into(logoImageView);

        loadLoginDetails();

        generateNewVerifyCode();

        mImgVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateNewVerifyCode();
            }
        });

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_login_onClick();
            }
        });

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_register_onClick(v);
            }
        });
    }

    private void loadLoginDetails() {
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");

        ET_username.setText(savedUsername);
        ET_password.setText(savedPassword);

        if (!savedPassword.isEmpty()) {
            cbRememberPassword.setChecked(true);
        }
    }

    private void saveLoginDetails(String username, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);

        if (cbRememberPassword.isChecked()) {
            editor.putString("password", password);
        } else {
            editor.putString("password", "");
        }

        editor.apply();
    }

    private void generateNewVerifyCode() {
        Bitmap bitmap = Captcha.getInstance().getBitmap();
        mImgVerifyCode.setImageBitmap(bitmap);
        mCurrentVerifyCode = Captcha.getInstance().getCode();
        Log.d(TAG, "New verify code generated: " + mCurrentVerifyCode);
    }

    private boolean validateVerifyCode(String inputCode) {
        return inputCode.equalsIgnoreCase(mCurrentVerifyCode);
    }

    public void btn_register_onClick(View v) {
        String username = ET_username.getText().toString();
        String password = ET_password.getText().toString();
        String inputVerifyCode = ET_verifyCode.getText().toString();

        if (username.equals("") || password.equals("")) {
            Toast.makeText(this, "还没有填写用户名和密码！", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 10) {
            Toast.makeText(this, "密码至少为10位！", Toast.LENGTH_SHORT).show();
        } else if (username.contains("\\") || username.contains("/") || username.contains(":") || username.contains("*")
                || username.contains("?") || username.contains("\"") || username.contains("<") || username.contains(">")
                || username.contains("|")) {
            Toast.makeText(this, "用户名中请勿包含\n \\ / : * ? \" < > |等特殊字符", Toast.LENGTH_SHORT).show();
        } else if (!validateVerifyCode(inputVerifyCode)) {
            Toast.makeText(this, "验证码错误，请重新输入！", Toast.LENGTH_SHORT).show();
            generateNewVerifyCode();
        } else if (!cbProtocol.isChecked()) {
            Toast.makeText(this, "请先阅读并同意用户协议和隐私政策！", Toast.LENGTH_SHORT).show();
        } else {
            Account temp = new Account(username, password, mContext);
            Intent intent;
            if (temp.saveAccount()) {
                saveLoginDetails(username, password);
                Toast.makeText(this, "注册成功! \n 为您直接登录", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Registration successful, logging in...");
                intent = new Intent(LoginActivity.this, IndexActivity.class);
                intent.putExtra("userName", username);
                startActivity(intent);
            } else {
                Toast.makeText(this, "注册失败:( \n 该用户已存在，请尝试其他用户名", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void btn_login_onClick() {
        String username = ET_username.getText().toString();
        String password = ET_password.getText().toString();
        String inputVerifyCode = ET_verifyCode.getText().toString();

        if (username.equals("") || password.equals("")) {
            Toast.makeText(this, "还没有填写用户名和密码！", Toast.LENGTH_SHORT).show();
        } else if (!validateVerifyCode(inputVerifyCode)) {
            Toast.makeText(this, "验证码错误，请重新输入！", Toast.LENGTH_SHORT).show();
            generateNewVerifyCode();
        } else if (!cbProtocol.isChecked()) {
            Toast.makeText(this, "请先阅读并同意用户协议和隐私政策！", Toast.LENGTH_SHORT).show();
        } else {
            Account temp = new Account(username, password, mContext);
            boolean isLoginSuccessful = temp.verifyAccount();

            Log.d(TAG, "Username: " + username);
            Log.d(TAG, "Password: " + password);
            Log.d(TAG, "Verify code: " + inputVerifyCode);
            Log.d(TAG, "Current verify code: " + mCurrentVerifyCode);

            if (isLoginSuccessful) {
                saveLoginDetails(username, password);
                Toast.makeText(this, "登录成功!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Login successful, navigating to IndexActivity...");
                Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
                intent.putExtra("userName", username);
                startActivity(intent);
            } else {
                Toast.makeText(this, "登录失败，请检查用户名和密码是否正确", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
