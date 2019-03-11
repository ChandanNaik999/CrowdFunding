package com.seproject.crowdfunder.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.Utils.util;
import com.seproject.crowdfunder.models.User;

public class RegisterActivity extends AppCompatActivity {



    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mName;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        // Set up the login form.
        mEmailView =  findViewById(R.id.reg_email);
        mName = findViewById(R.id.nameRegister);

        mPasswordView = (EditText) findViewById(R.id.reg_password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.btnRegister);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }






    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mName.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String name = mName.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password) && password.length() == 0) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid name, if the user entered one.
        if (!TextUtils.isEmpty(name) &&  password.length() == 0) {
            mName.setError(getString(R.string.error_invalid_name));
            focusView = mName;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            //create user
            auth.createUserWithEmailAndPassword(mEmailView.getText().toString(), mPasswordView
            .getText().toString())
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                FirebaseUser FUser = FirebaseAuth.getInstance().getCurrentUser();
                                if (FUser != null) {
                                    util.user = new User();
                                    util.user.setUid(FUser.getUid());
                                    uploadUserDetails(util.user.getUid());
                                }
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                finish();
                            }
                        }
                    });
        }
    }

    private void uploadUserDetails(String uid) {
        myRef = database.getReference("sub/user/" + uid );
        myRef.child("name").setValue(mName.getText().toString());
        myRef.child("bookmarks").setValue(0);
        myRef.child("can_notify").setValue(1);
        myRef.child("is_account_active").setValue(1);
        myRef.child("is_deactivated").setValue(0);
        myRef.child("is_donor").setValue(0);
        myRef.child("is_online").setValue(1);
        myRef.child("is_requestor").setValue(0);
        myRef.child("mail").setValue(mEmailView.getText().toString());
        myRef.child("no_of_donations").setValue(0);
        myRef.child("no_of_requests").setValue(0);
        myRef.child("rating").setValue(0);
        myRef.child("total_donated").setValue(0);
        myRef.child("total_requested").setValue(0);


        util.user.setEmail(mEmailView.getText().toString());
        util.user.setRating(0);
        util.user.setName(mName.getText().toString());

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 8;
    }

    public void backClicked(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }


}
