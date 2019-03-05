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
import com.google.firebase.database.ValueEventListener;
import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.Utils.FirebaseMethods;
import com.seproject.crowdfunder.Chandan.Adapter.RequestShortDetailsAdapter;
import com.seproject.crowdfunder.models.RequestShortDetails;

import java.util.ArrayList;
import java.util.List;

public class TrendingFragment extends Fragment {
    RecyclerView recyclerViewList;
    private List<RequestShortDetails> requestShortDetails = new ArrayList<>();
    RequestShortDetails requestShortDetailsFromdatabase;
    RequestShortDetailsAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
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
        DatabaseReference myRef = FirebaseMethods.createFirebaseReferance();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                requestShortDetailsFromdatabase = new RequestShortDetails();
                String id = "Xjpxl1gzfFfpoAVUOjNdvdNKtCH3";
                requestShortDetailsFromdatabase.setId(dataSnapshot.child("sub/request/asfgasg/user_id").getValue().toString());
                requestShortDetailsFromdatabase.setName(dataSnapshot.child("sub/request/asfgasg/name").getValue().toString());
                requestShortDetailsFromdatabase.setTitle(dataSnapshot.child("sub/request/asfgasg/title").getValue().toString());
                requestShortDetailsFromdatabase.setamountRequested(Integer.parseInt(dataSnapshot.child("sub/request/asfgasg/amount_required").getValue().toString()));
                requestShortDetailsFromdatabase.setBackers(Integer.parseInt(dataSnapshot.child("sub/request/asfgasg/backers").getValue().toString()));
                requestShortDetailsFromdatabase.setLocation(dataSnapshot.child("sub/request/asfgasg/location").getValue().toString());
                requestShortDetailsFromdatabase.setpercentFunded(Integer.parseInt(dataSnapshot.child("sub/request/asfgasg/amount_funded").getValue().toString())*100/requestShortDetailsFromdatabase.getamountRequested());
                requestShortDetailsFromdatabase.setRating(Integer.parseInt(dataSnapshot.child("sub/request/asfgasg/rating").getValue().toString()));
                requestShortDetailsFromdatabase.settimeLeft(Integer.parseInt(dataSnapshot.child("sub/request/asfgasg/days_left").getValue().toString()));
                requestShortDetailsFromdatabase.setProfilePic(R.drawable.app_icon);
                requestShortDetailsFromdatabase.setBookmarked(false);
                requestShortDetails.clear();
                requestShortDetails.add(requestShortDetailsFromdatabase);
                adapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });



    }
}
