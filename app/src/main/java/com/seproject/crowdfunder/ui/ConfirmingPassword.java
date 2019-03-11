package com.seproject.crowdfunder.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.seproject.crowdfunder.R;

public class ConfirmingPassword extends AppCompatActivity {

    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirming_password);
        password = findViewById(R.id.editText);
    }

    public void ContinueClicked(View view) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Email", 0); // 0 - for private mode
        String email = pref.getString("Email",null);
        if(password.getText().toString().matches("abcd"))
            startActivity(new Intent(this, ResonForDeactivating.class));
        else
            Toast.makeText(this, "Wrong Password ", Toast.LENGTH_SHORT).show();
    }

    private void attemptLogin() {

        // Reset errors.
        password.setError(null);

        // Store values at the time of the login attempt.
        String email = password.getText().toString();

        boolean cancel = false;
        View focusView = null;



        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            password.setError(getString(R.string.error_field_required));
            focusView = password;
            cancel = true;
        } else if (!isEmailValid(email)) {
            password.setError(getString(R.string.error_invalid_email));
            focusView = password;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 8;
    }

    public void asdf(View view) {
        attemptLogin();
    }
}
