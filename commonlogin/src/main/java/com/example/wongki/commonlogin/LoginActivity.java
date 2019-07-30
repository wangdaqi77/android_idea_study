package com.example.wongki.commonlogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.wongki.commonlib.modularization.ILoginProviderService;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginSuccess(View view) {
        // TODO 登陆成功
        setResult(ILoginProviderService.LOGIN_SUCCESS);
        finish();
    }
}
