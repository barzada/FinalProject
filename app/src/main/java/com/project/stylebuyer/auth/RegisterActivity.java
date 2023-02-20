package com.project.stylebuyer.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.project.stylebuyer.R;
import com.project.stylebuyer.firebase.Api;
import com.project.stylebuyer.firebase.Customer;
import com.project.stylebuyer.home.HomeActivity;
import com.project.stylebuyer.interfaces.LoginCallBack;
import com.project.stylebuyer.utils.Validator;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout signup_TIL_fullName, signup_TIL_email, signup_TIL_password, signup_TIL_confirmPassword;
    private MaterialButton signup_BTN_signup;
    private Api api;
    private Validator.Builder full_name_builder, email_builder, password_builder, confirmPassword_builder;
    private ProgressBar signup_PB_loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initViews();
        addValidation();
        initVars();

    }

    private void initVars() {
        api = new Api();
        api.setLoginCallBack(new LoginCallBack() {
            @Override
            public void loginSuccess() {
                signup_PB_loading.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void loginFailed(String msg) {
                Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                signup_PB_loading.setVisibility(View.INVISIBLE);
            }
        });
        signup_PB_loading.setVisibility(View.INVISIBLE);

        signup_BTN_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(! full_name_builder.getStatus() || !email_builder.getStatus() || !password_builder.getStatus() || !confirmPassword_builder.getStatus())
                {
                    Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                String email = signup_TIL_email.getEditText().getText().toString();
                String password = signup_TIL_password.getEditText().getText().toString();
                String name = signup_TIL_fullName.getEditText().getText().toString();
                Customer client = new Customer(name, email, password);
                api.login(client, true);
                signup_PB_loading.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initViews() {
        signup_BTN_signup = findViewById(R.id.signup_BTN_signup);
        signup_TIL_fullName = findViewById(R.id.signup_TIL_fullName);
        signup_TIL_email = findViewById(R.id.signup_TIL_email);
        signup_TIL_password = findViewById(R.id.signup_TIL_password);
        signup_TIL_confirmPassword = findViewById(R.id.signup_TIL_confirmPassword);
        signup_PB_loading = findViewById(R.id.signup_PB_loading);
    }

    private void addValidation() {
        full_name_builder = Validator.Builder
                .make(signup_TIL_fullName)
                .addWatcher(new Validator.Watcher_NotEmpty("Name can't be empty"))
                .addWatcher(new Validator.Watcher_FullName("Enter the full name"))
                .build();

        email_builder = Validator.Builder
                .make(signup_TIL_email)
                .addWatcher(new Validator.Watcher_NotEmpty("Email can't be empty"))
                .addWatcher(new Validator.Watcher_Email("Email not valid"))
                .build();

        password_builder = Validator.Builder
                .make(signup_TIL_password)
                .addWatcher(new Validator.Watcher_NotEmpty("Password can't be empty"))
                .addWatcher(new Validator.Watcher_password("Wrong password format"))
                .build();

        confirmPassword_builder = Validator.Builder
                .make(signup_TIL_confirmPassword)
                .addWatcher(new Validator.Watcher_NotEmpty("Password can't be empty"))
                .addWatcher(new Validator.Watcher_Equal("Confirm password doesn't match password ", signup_TIL_password.getEditText()))
                .build();
    }
}