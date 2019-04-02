package com.seproject.crowdfunder.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.Utils.util;
import com.seproject.crowdfunder.models.Request;
import com.seproject.crowdfunder.models.RequestHistory;
import com.seproject.crowdfunder.models.User;

import java.util.Calendar;
import java.util.Objects;
/** Adarsh  - 17CO204 */
public class RequestActivity extends AppCompatActivity {

    Request request_main;
    TextView username, title, desc, amount_requested, donor_count, days_left, location;
    int amount;
    private Calendar calendar;
    RequestHistory requestHistory;
    private int year, month, day;
    int wallet_amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String request_id = getIntent().getStringExtra("request_id");
        for (Request request : util.requests){
            if(request_id.matches(request.getRequest_id()+"")){
                request_main = request;
                break;
            }
        }

        username = findViewById(R.id.username);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        amount_requested = findViewById(R.id.amount_requested);
        donor_count = findViewById(R.id.donor_count);
        days_left = findViewById(R.id.days_left);
        location = findViewById(R.id.location);

        username.setText("Hosted by: " + request_main.getUser_name());
        title.setText(request_main.getTitle());
        desc.setText("Description: " + request_main.getDesc());
        amount_requested.setText("amount required:" + request_main.getAmount_required());
        donor_count.setText("Number of backers: " + request_main.getBackers());
        days_left.setText(request_main.getDays_left() + " days left for the campaign to close");
        location.setText("This request was hosted from: " + request_main.getLocation());

        DatabaseReference myRef = database.getReference(util.path_base_path + util.path_user + util.user.getUid() );
        myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wallet_amount = Integer.parseInt(dataSnapshot.child("wallet").getValue().toString());
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

    public void pay(View view) {
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

                if (name.getText().toString().length() == 0)
                    Toast.makeText(RequestActivity.this, "Enter amount",Toast.LENGTH_SHORT).show();
                else if(Integer.parseInt(name.getText().toString()) == 0){
                    Toast.makeText(RequestActivity.this, "Enter amount",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dialog.dismiss();
                    amount = Integer.parseInt(name.getText().toString());
                    if (amount > wallet_amount)
                        Toast.makeText(RequestActivity.this, "You do not have that much money", Toast.LENGTH_SHORT).show();
                    else {
                        setDetails();
                        contribute();
                        finish();
                    }


                }
            }
        });

        dialog.show();

    }

    public void setDetails(){
        requestHistory = new RequestHistory();
        requestHistory.setTo(request_main.getUid());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(util.path_base_path + util.path_user + request_main.getUid() );
        myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myRef.removeEventListener(this);
                int prev_amount = Integer.parseInt(dataSnapshot.child("wallet").getValue().toString());
                DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference(util.path_base_path + util.path_user + request_main.getUid());
                myRef1.child("wallet").setValue(amount+prev_amount);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        final DatabaseReference myRef2 = database.getReference(util.path_base_path + util.path_user + util.user.getUid() );
        myRef2.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int prev_amount = Integer.parseInt(dataSnapshot.child("wallet").getValue().toString());
                DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference(util.path_base_path + util.path_user + util.user.getUid());
                myRef1.child("wallet").setValue(prev_amount - amount);
                myRef2.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        requestHistory.setDate(showDate(year, month+1, day));
        requestHistory.setFrom(util.user.getUid());
        requestHistory.setAmount(amount);
        requestHistory.setRequest_id(request_main.getRequest_id() + "");

//        DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference(util.path_base_path + util.path_donations);
//        myRef1.child("wallet").setValue(amount);
    }

    private String showDate(int year, int month, int day) {
        return new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year).toString();
    }

    public void contribute() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(util.path_base_path);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int no_of_donations = Integer.parseInt(Objects.requireNonNull(dataSnapshot.child(util.path_no_of_donations).getValue()).toString());

                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference(util.path_base_path);
                mRef.child(util.path_no_of_donations).setValue(no_of_donations + 1);


                int id = no_of_donations + 1;
                mRef = FirebaseDatabase.getInstance().getReference(util.path_base_path + util.path_donations + id + "/");
                mRef.setValue(requestHistory);



                databaseReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


    }

    public void rateUser(View view){
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        final float rating = ratingBar.getRating();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(util.path_base_path + util.path_user );
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = new User();
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("");
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    if(request_main.getUid().matches(dataSnapshot1.child("uid").getValue().toString())){
                        user = dataSnapshot1.getValue(User.class);
                        myRef = dataSnapshot1.getRef();
                    }
                }

                float new_rating = (user.getRating()*user.getUser_rated() + rating)/(user.getUser_rated() + 1);
                user.setRating(new_rating);
                user.setUser_rated(user.getUser_rated() + 1);
                myRef.setValue(user);




                databaseReference.removeEventListener(this);



            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
}
