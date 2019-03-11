package com.seproject.crowdfunder.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.seproject.crowdfunder.R;

public class ResonForDeactivating extends AppCompatActivity {

    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reson_for_deactivating);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("sub/user/Xjpxl1gzfFfpoAVUOjNdvdNKtCH3/is_deactivated");


    }

    public void deactivate_account(View view) {
        myRef.setValue("1");
    }
}
