package com.seproject.crowdfunder.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.seproject.crowdfunder.models.Request;
import com.seproject.crowdfunder.models.RequestShortDetails;

import java.util.Comparator;

import static com.seproject.crowdfunder.Utils.util.bookmarks;
import static com.seproject.crowdfunder.Utils.util.requestShortDetailsList;
import static com.seproject.crowdfunder.Utils.util.requests;
import static com.seproject.crowdfunder.ui.MainActivity.yourLocation;


/** Chandan - 17CO212 */


public class TrendingFragment extends Fragment {
    private static final String TAG = "Trending Fragment";
    RecyclerView recyclerViewList;
   // private List<RequestShortDetails> requestShortDetails = new ArrayList<>();
    RequestShortDetails requestShortDetailsFromdatabase;
    public static RequestShortDetailsAdapter adapter;
    float rating = 0;
    FirebaseDatabase database;
    int no_of_requests = 0;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        database = FirebaseDatabase.getInstance();
        prepareRequestList();

        recyclerViewList =  view.findViewById(R.id.list);
         adapter= new RequestShortDetailsAdapter(getContext(), requestShortDetailsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewList.setLayoutManager(mLayoutManager);
        recyclerViewList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewList.setAdapter(adapter);



        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        prepareRequestList();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        return view;
    }

    public void prepareRequestList() {


        final DatabaseReference myReference = database.getReference(util.path_base_path + util.path_user +util.user.getUid() + "/"+ util.path_bookmarks);
        myReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookmarks.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String id = postSnapshot.getValue(String.class);
                    bookmarks.add(id);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        requestShortDetailsFromdatabase = new RequestShortDetails();
        requests.clear();
        final DatabaseReference myRef = database.getReference(util.path_base_path + util.path_requests);
        myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                requestShortDetailsList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Log.d(TAG,bookmarks.size() + "");
                     final Request request = postSnapshot.getValue(Request.class);
                    boolean bookmarked = false;
                    for (String id : bookmarks){
                        if (id.matches(request.getRequest_id()+"")) {
                            bookmarked = true;
                            request.setBookmarked(true);
                            Log.d(TAG,id);
                        }
                    }
                    assert request != null;
                    double dist = util.kilometerDistanceBetweenPoints(yourLocation.getLatitude(), yourLocation.getLongitude(), (double)request.getLat() , (double) request.getLon());
                    if(dist < 100)
                        request.setViews(request.getViews()+10);
                    else if(dist < 200)
                        request.setViews(request.getViews()+5);
                    else if(dist < 300)
                        request.setViews(request.getViews()+3);


                     requests.add(request);
//
//                    final DatabaseReference user = database.getReference(util.path_base_path + util.path_user);
//                    user.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            // This method is called once with the initial value and again
//                            // whenever data at this location is updated.
//                            for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
//                                if(request.getUid().matches(dataSnapshot1.child("uid").getValue().toString())){
//                                    rating = Float.parseFloat(dataSnapshot1.child("rating").getValue().toString());
//                                    break;
//                                }
//                            }
//                            adapter.notifyDataSetChanged();
//                            user.removeEventListener(this);
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError error) {
//                            // Failed to read value
//                            Log.w(TAG, "Failed to read value.", error.toException());
//                        }
//                    });

                     RequestShortDetails requestShortDetail= new RequestShortDetails(request.getRequest_id()+""
                             ,request.getTitle(), R.mipmap.ic_launcher_round, request.getUser_name()
                             , request.getLocation(),1, bookmarked
                             , (int)request.getAmount_required(),request.getBackers(),request.getDays_left()
                             , (int)(request.getAmount_funded()  *100 /request.getAmount_required()),request.getViews());
                    requestShortDetailsList.add(requestShortDetail);
                }
                NearYouFragment.prepareRequestList();

                myRef.removeEventListener(this);
                requestShortDetailsList.sort(new Comparator<RequestShortDetails>() {
                    @Override
                    public int compare(RequestShortDetails o1, RequestShortDetails o2) {
                        if(o1.getViews() == o2.getViews())
                            return  0;
                        else if(o1.getViews() > o2.getViews())
                            return 1;
                        else
                            return -1;
                    }
                }.reversed());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

    }


}
