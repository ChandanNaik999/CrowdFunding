package com.seproject.crowdfunder.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.seproject.crowdfunder.R;

public class FillDetailsPageForStartingReqest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_details_page_for_starting_reqest);
    }

//    public void gotocheck(View view) {
//        startActivity(new Intent(this, CheckDetailsPageForStartingReqest.class));
//    }

    public void gotocheck(View view) {
        startActivity(new Intent(this, DocumentUpload.class));
        finish();
    }

    public void gotToDocumentUpload(View view) {

    }
}
