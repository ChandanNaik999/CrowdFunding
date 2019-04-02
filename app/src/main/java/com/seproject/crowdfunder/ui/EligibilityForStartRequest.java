package com.seproject.crowdfunder.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.seproject.crowdfunder.R;
/**   Mahesh - 17CO216 */
public class EligibilityForStartRequest extends AppCompatActivity {


    CheckBox checkBox1, checkBox2;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahesh__elgibility);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);

    }

    public void goToFillDetail(View view) {
        if(checkBox2.isChecked() && checkBox1.isChecked()){
            startActivity(new Intent(this, FillDetailsPageForStartingReqest.class));
            finish();
        }
        else
            Toast.makeText(EligibilityForStartRequest.this, "Accept the requirements", Toast.LENGTH_SHORT).show();

        }

}
