package com.ridemgmtsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.ridemgmtsystem.R;
import com.ridemgmtsystem.Utils.StoredPreferences;
import com.ridemgmtsystem.models.UserAccount;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.account_user_name) TextInputEditText accountUsername;
    @BindView(R.id.account_password) TextInputEditText accountPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.create_account_button)
    public void onCreateAccountButtonClick() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClick() {
        if(!accountUsername.getText().toString().isEmpty() &&
                !accountPassword.getText().toString().isEmpty()) {
            ArrayList<UserAccount> accountList = StoredPreferences.getSavedUserAccountsListFromPreferences(this);
            if(accountList.size() > 0) {
                for(UserAccount userAccount : accountList) {
                    if(userAccount.email.equalsIgnoreCase(accountUsername.getText().toString()) &&
                            userAccount.password.equalsIgnoreCase(accountPassword.getText().toString())) {
                        StoredPreferences.saveLoggedInUsername(this, userAccount.email);
                        Intent intent = new Intent(this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            } else {
                Toast.makeText(this, "Account not found. Please check and try again",Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Please enter username and password and try again",Toast.LENGTH_LONG).show();
        }
    }
}