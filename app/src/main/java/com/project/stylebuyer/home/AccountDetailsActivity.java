package com.project.stylebuyer.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.project.stylebuyer.R;
import com.project.stylebuyer.firebase.Api;
import com.project.stylebuyer.utils.Validator;

public class AccountDetailsActivity extends AppCompatActivity {
    private TextInputLayout editAccount_TIL_fullName, editAccount_TIL_password, editAccount_TIL_confirmPassword;
    private MaterialButton editAccount_BTN_update;
    private Validator.Builder full_name_builder, passwordBuilder, confirmPasswordBuilder;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        findViews();
        addValidation();
        initVars();
    }

    private void initVars() {
        api = new Api();
        editAccount_TIL_fullName.getEditText().setText(api.getCurrentUser().getDisplayName());
        editAccount_BTN_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!full_name_builder.getStatus()){
                    Toast.makeText(AccountDetailsActivity.this, "You should fill all fields correct!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = editAccount_TIL_fullName.getEditText().getText().toString();
                api.updateUserFullName(name);
                if(passwordBuilder.getStatus() && confirmPasswordBuilder.getStatus())
                    api.changePassword(editAccount_TIL_password.getEditText().getText().toString());

                Intent returnIntent = new Intent();
                returnIntent.putExtra("NAME", name);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    private void addValidation() {
        full_name_builder = Validator.Builder
                .make(editAccount_TIL_fullName)
                .addWatcher(new Validator.Watcher_NotEmpty("Name can't be empty"))
                .addWatcher(new Validator.Watcher_FullName("Enter the full name"))
                .build();

        passwordBuilder = Validator.Builder
                .make(editAccount_TIL_password)
                .addWatcher(new Validator.Watcher_password("Password format not correct"));

        confirmPasswordBuilder = Validator.Builder
                .make(editAccount_TIL_confirmPassword)
                .addWatcher(new Validator.Watcher_Equal("Confirm password doesn't match password ",
                        editAccount_TIL_password.getEditText()))
                .build();
    }

    private void findViews() {
        editAccount_TIL_fullName = findViewById(R.id.editAccount_TIL_fullName);
        editAccount_BTN_update = findViewById(R.id.editAccount_BTN_update);
        editAccount_TIL_confirmPassword = findViewById(R.id.editAccount_TIL_confirmPassword);
        editAccount_TIL_password = findViewById(R.id.editAccount_TIL_password);
    }
}