package com.seproject.crowdfunder.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.seproject.crowdfunder.R;

public class asd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asd);
    }

    public void goToFillDetails(View view) {
        startActivity(new Intent(this, Mahesh_Elgibility.class));
        finish();
    }
}
