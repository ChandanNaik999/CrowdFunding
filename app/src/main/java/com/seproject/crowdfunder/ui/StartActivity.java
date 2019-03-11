package com.seproject.crowdfunder.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.seproject.crowdfunder.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
    }

    public void LoginClicked(View view) {
        startActivity(new Intent(this,LoginActivity.class));
    }

    public void RegisterClicked(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
