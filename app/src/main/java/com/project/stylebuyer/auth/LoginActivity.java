package com.project.stylebuyer.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;
import com.project.stylebuyer.R;
import com.project.stylebuyer.firebase.Api;
import com.project.stylebuyer.firebase.Customer;
import com.project.stylebuyer.home.HomeActivity;
import com.project.stylebuyer.interfaces.LoginCallBack;

public class LoginActivity extends AppCompatActivity {
    private Button login_BTN_createAccount;
    private MaterialButton login_BTN_login;
    private TextInputLayout login_TIL_email, login_TIL_password;
    private Api api;
    private ProgressBar login_PB_loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initVars();

    }

    private void initVars() {
        login_PB_loading.setVisibility(View.INVISIBLE);
        login_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login_TIL_email.getEditText().getText().toString();
                String password = login_TIL_password.getEditText().getText().toString();
                if(email.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Email or Password can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                login_PB_loading.setVisibility(View.VISIBLE);
                Customer client = new Customer()
                        .setEmail(email)
                        .setPassword(password);
                api.login(client, false);
            }
        });

        login_BTN_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void initView() {
        login_BTN_login = findViewById(R.id.login_BTN_login);
        login_BTN_createAccount = findViewById(R.id.login_BTN_createAccount);
        login_TIL_email = findViewById(R.id.login_TIL_email);
        login_TIL_password = findViewById(R.id.login_TIL_password);
        login_PB_loading = findViewById(R.id.login_PB_loading);

    }

    public void openHomeScreen(){
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        api = new Api();
        api.setLoginCallBack(new LoginCallBack() {
            @Override
            public void loginSuccess() {
                openHomeScreen();
            }

            @Override
            public void loginFailed(String msg) {
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                login_PB_loading.setVisibility(View.INVISIBLE);
            }
        });
        FirebaseUser user = api.getCurrentUser();
        if(user != null){
            openHomeScreen();
        }
    }
}