package com.seproject.crowdfunder.ui;

import android.app.Dialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.Utils.util;
import com.seproject.crowdfunder.fragment.NearYouFragment;
import com.seproject.crowdfunder.models.Request;
import com.seproject.crowdfunder.models.RequestShortDetails;

import java.util.Comparator;

import static com.seproject.crowdfunder.Utils.util.requestShortDetailsList;
import static com.seproject.crowdfunder.Utils.util.requests;
/** adarsh 17CO204 */
public class Wallet extends AppCompatActivity {

    FirebaseDatabase database;
    TextView amount;
    int wallet_amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        database = FirebaseDatabase.getInstance();
        amount = findViewById(R.id.amount);
        DatabaseReference myRef = database.getReference(util.path_base_path + util.path_user + util.user.getUid() );
        myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wallet_amount = Integer.parseInt(dataSnapshot.child("wallet").getValue().toString());
                amount.setText("Rs. " + dataSnapshot.child("wallet").getValue().toString() + "/-");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    public void backClicked(View view) {
        super.onBackPressed();
    }

    public void addMoney(View view) {
            // custom dialog
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.layout_dialog_pay);
            dialog.setTitle("Update Name...");

            // set the custom dialog components - text, image and button


            Button cancel = (Button) dialog.findViewById(R.id.cancel);
            // if button is clicked, close the custom dialog
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            final EditText name = dialog.findViewById(R.id.name_input);
            Button ok = (Button) dialog.findViewById(R.id.ok);
            // if button is clicked, close the custom dialog
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (name.getText().toString().length() ==  0)
                        Toast.makeText(Wallet.this, "Enter amount",Toast.LENGTH_SHORT).show();
                    else
                    {
                        DatabaseReference myRef = database.getReference(util.path_base_path + util.path_user + util.user.getUid() );
                        int new_amount = Integer.parseInt(name.getText().toString()) + wallet_amount;
                        myRef.child("wallet").setValue( new_amount );
                        amount.setText("Rs. " + new_amount + "/-");
                        dialog.dismiss();
                    }
                }
            });

            dialog.show();

    }
}
