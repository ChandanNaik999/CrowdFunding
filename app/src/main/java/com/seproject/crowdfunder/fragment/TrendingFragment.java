package com.seproject.crowdfunder.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class TrendingFragment extends Fragment {
    RecyclerView recyclerViewList;
    private List<RequestShortDetails> requestShortDetails = new ArrayList<>();
    RequestShortDetails requestShortDetailsFromdatabase;
    RequestShortDetailsAdapter adapter;
    FirebaseDatabase database;
    int no_of_requests = 0;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        database = FirebaseDatabase.getInstance();
        prepareRequestList();
        recyclerViewList =  view.findViewById(R.id.list);
         adapter= new RequestShortDetailsAdapter(getContext(), requestShortDetails);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewList.setLayoutManager(mLayoutManager);
        recyclerViewList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewList.setAdapter(adapter);


        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        return view;
    }

    public void prepareRequestList() {

        DatabaseReference myRef = database.getReference("sub/");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 no_of_requests = Integer.parseInt(dataSnapshot.child("no_of_requests").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        myRef = database.getReference("sub/request/");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                int i = 1;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    requestShortDetailsFromdatabase = new RequestShortDetails();
                    //requestShortDetailsFromdatabase.setId(dataSnapshot.child(i+"/user_id").getValue().toString());
                    requestShortDetailsFromdatabase.setName(postSnapshot.child("/name").getValue().toString());
                    requestShortDetailsFromdatabase.setTitle(postSnapshot.child("/title").getValue().toString());
                    requestShortDetailsFromdatabase.setamountRequested(Integer.parseInt(postSnapshot.child("/amount_required").getValue().toString()));
                    requestShortDetailsFromdatabase.setBackers(Integer.parseInt(postSnapshot.child("/backers").getValue().toString()));
                    requestShortDetailsFromdatabase.setLocation(postSnapshot.child("/location").getValue().toString());
                    requestShortDetailsFromdatabase.setpercentFunded(Integer.parseInt(postSnapshot.child("/amount_funded").getValue().toString())*100/requestShortDetailsFromdatabase.getamountRequested());
                    requestShortDetailsFromdatabase.setRating(Float.parseFloat(postSnapshot.child("/rating").getValue().toString()));
                    requestShortDetailsFromdatabase.settimeLeft(Integer.parseInt(postSnapshot.child("/days_left").getValue().toString()));
                    requestShortDetailsFromdatabase.setProfilePic(R.drawable.app_icon);
                    requestShortDetailsFromdatabase.setBookmarked(false);
                    requestShortDetails.clear();
                    i++;
                    requestShortDetails.add(requestShortDetailsFromdatabase);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });



    }
}
