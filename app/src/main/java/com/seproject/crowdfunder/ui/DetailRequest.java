package com.seproject.crowdfunder.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.Utils.util;
import com.seproject.crowdfunder.adapter.HistoryAdapter;
import com.seproject.crowdfunder.adapter.nameAdapter;
import com.seproject.crowdfunder.models.DonationDetail;
import com.seproject.crowdfunder.models.Request;
import com.seproject.crowdfunder.models.RequestHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**  Ratan  - 17CO211 */
public class DetailRequest extends AppCompatActivity {
    private static final String TAG = "DetailRequest";
    private List<RequestHistory> requestList = new ArrayList<>();
    int request_id;
    nameAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_request);
        request_id = getIntent().getIntExtra("request_id",0);
        Toast.makeText(this, request_id + "", Toast.LENGTH_SHORT).show();

        adapter = new nameAdapter(requestList,this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        prepareDonorData();





    }

    private void prepareDonorData() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(util.path_base_path+util.path_donations);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    if(Objects.requireNonNull(Integer.parseInt(dataSnapshot1.child("request_id").getValue().toString()) == request_id))
                        requestList.add(dataSnapshot1.getValue(RequestHistory.class));
                }
                adapter.notifyDataSetChanged();
                myRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }

    public void backClicked(View view) {
        super.onBackPressed();
    }

    public void askRating(View view) {
    }
}
