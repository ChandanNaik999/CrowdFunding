package com.seproject.crowdfunder.ui;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.Utils.FirebaseMethods;
import com.seproject.crowdfunder.Utils.util;
import com.seproject.crowdfunder.adapter.RequestShortDetailsAdapter;
import com.seproject.crowdfunder.models.RequestShortDetails;

import java.util.ArrayList;
import java.util.List;

public class BookmarksActivity extends AppCompatActivity {

    private static final String TAG = "Bookmark";
    ArrayList<String> bookmarks = new ArrayList<>();

    RecyclerView recyclerViewList;
    private List<RequestShortDetails> requestShortDetails = new ArrayList<>();
    RequestShortDetails requestShortDetailsFromdatabase;
    RequestShortDetailsAdapter adapter;
    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked);
        database = FirebaseDatabase.getInstance();


        recyclerViewList =  findViewById(R.id.list);
        adapter= new RequestShortDetailsAdapter(this, requestShortDetails);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerViewList.setLayoutManager(mLayoutManager);
        recyclerViewList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewList.setAdapter(adapter);

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        myRef = database.getReference("sub/user/" + util.user.getUid() + "/bookmarks");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                     bookmarks.set(i,postSnapshot.getValue(String.class));
                }

                updateView();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void updateView() {
        if(bookmarks.size() == 0)
            Toast.makeText(BookmarksActivity.this, "No Bookmarks", Toast.LENGTH_SHORT).show();
        else
        {
            int i =0;
            for( ; i<bookmarks.size();i++ ){
                myRef = database.getReference("sub/request/" + bookmarks.get(i));
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            requestShortDetailsFromdatabase = new RequestShortDetails();
                            requestShortDetailsFromdatabase.setId(dataSnapshot.child("/user_id").getValue().toString());
                            requestShortDetailsFromdatabase.setName(dataSnapshot.child("/name").getValue().toString());
                            requestShortDetailsFromdatabase.setTitle(dataSnapshot.child("/title").getValue().toString());
                            requestShortDetailsFromdatabase.setamountRequested(Integer.parseInt(dataSnapshot.child("/amount_required").getValue().toString()));
                            requestShortDetailsFromdatabase.setBackers(Integer.parseInt(dataSnapshot.child("/backers").getValue().toString()));
                            requestShortDetailsFromdatabase.setLocation(dataSnapshot.child("/location").getValue().toString());
                            requestShortDetailsFromdatabase.setpercentFunded(Integer.parseInt(dataSnapshot.child("/amount_funded").getValue().toString())*100/requestShortDetailsFromdatabase.getamountRequested());
                            requestShortDetailsFromdatabase.setRating(Float.parseFloat(dataSnapshot.child("/rating").getValue().toString()));
                            requestShortDetailsFromdatabase.settimeLeft(Integer.parseInt(dataSnapshot.child("/days_left").getValue().toString()));
                            requestShortDetailsFromdatabase.setProfilePic(R.drawable.app_icon);
                            requestShortDetailsFromdatabase.setBookmarked(false);
                            requestShortDetails.clear();
                            requestShortDetails.add(requestShortDetailsFromdatabase);
                            adapter.notifyDataSetChanged();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        }

    }
}
