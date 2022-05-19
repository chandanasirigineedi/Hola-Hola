package com.ridemgmtsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;
import com.ridemgmtsystem.R;
import com.ridemgmtsystem.Utils.StoredPreferences;
import com.ridemgmtsystem.models.UserAccount;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateAccountActivity extends AppCompatActivity {

    @BindView(R.id.account_user_name) TextInputEditText accountUsername;
    @BindView(R.id.account_mobile_number) TextInputEditText accountMobileNumber;
    @BindView(R.id.account_password) TextInputEditText accountPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @OnClick(R.id.create_account_back_button)
    public void onBackButtonClick() {
        finish();
    }

    @OnClick(R.id.create_button)
    public void onCreateButtonClick() {
        if(!accountUsername.getText().toString().isEmpty() &&
                !accountMobileNumber.getText().toString().isEmpty() &&
                !accountPassword.getText().toString().isEmpty()) {
            UserAccount userAccount = new UserAccount();
            userAccount.email = accountUsername.getText().toString();
            userAccount.mobileNumber = Integer.parseInt(accountMobileNumber.getText().toString());
            userAccount.password = accountPassword.getText().toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to Create Account with username "+userAccount.email+" ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ArrayList<UserAccount> oldAccountList = StoredPreferences.getSavedUserAccountsListFromPreferences(CreateAccountActivity.this);
                            oldAccountList.add(userAccount);
                            StoredPreferences.saveUserAccountListInPreferences(CreateAccountActivity.this, oldAccountList);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        }
    }
}