package com.seproject.crowdfunder.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.Utils.util;
import com.seproject.crowdfunder.adapter.RequestShortDetailsAdapter;
import com.seproject.crowdfunder.models.DistanceRequest;
import com.seproject.crowdfunder.models.Request;
import com.seproject.crowdfunder.models.RequestShortDetails;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.seproject.crowdfunder.Utils.util.getLocation;
import static com.seproject.crowdfunder.ui.MainActivity.yourLocation;
import static com.seproject.crowdfunder.Utils.util.requests;

/**  Kaushiq - 17CO131 */
public class NearYouFragment extends Fragment {
    private static final String TAG = "NearYouFragment";
    RecyclerView recyclerViewList;
    public static List<RequestShortDetails> requestShortDetails = new ArrayList<>();
    RequestShortDetails requestShortDetailsFromdatabase;
    public static RequestShortDetailsAdapter adapter;
    //public static ArrayList<Request> requests = new ArrayList<>();
    FirebaseDatabase database;
    int no_of_requests = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        //database = FirebaseDatabase.getInstance();

        recyclerViewList = view.findViewById(R.id.list);
        Log.d(TAG, requestShortDetails.size()+"");
        adapter = new RequestShortDetailsAdapter(getContext(), requestShortDetails);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewList.setLayoutManager(mLayoutManager);
        recyclerViewList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewList.setAdapter(adapter);
//        prepareRequestList();
        return view;
    }




    //    public void prepareRequestList() {
//
//        requestShortDetailsFromdatabase = new RequestShortDetails();
//        final ArrayList<Request> list = new ArrayList<>();
//        final DatabaseReference myRef = database.getReference(util.path_base_path + util.path_requests);
//        final DatabaseReference myRef1 = database.getReference(util.path_base_path + util.path_user);
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                requestShortDetails.clear();
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Request request = postSnapshot.getValue(Request.class);
//                    RequestShortDetails requestShortDetail = new RequestShortDetails(request.getRequest_id() + "", request.getTitle(), R.mipmap.ic_launcher_round, request.getUser_name(), "surathkal", 1, false, (int) request.getAmount_required(), request.getBackers(), request.getDays_left(), (int) (request.getAmount_funded() * 100 / request.getAmount_required()), request.getViews());
//                    requestShortDetails.add(requestShortDetail);
//                }
//                adapter.notifyDataSetChanged();
//                myRef.removeEventListener(this);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//            }
//        });
//
//
//    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void prepareRequestList() {
        util.distanceRequestArrayList.clear();
        for (Request request : requests) {
            double dist = util.kilometerDistanceBetweenPoints(yourLocation.getLatitude(), yourLocation.getLongitude(), (double) request.getLat(), (double) request.getLon());
            util.distanceRequestArrayList.add(new DistanceRequest(request.getRequest_id() + "", dist));
            Log.d(TAG, dist+"");

        }



        util.distanceRequestArrayList.sort(new Comparator<DistanceRequest>() {
            @Override
            public int compare(DistanceRequest o1, DistanceRequest o2) {
                if (o1.getDistance() == o2.getDistance())
                    return 0;
                else if (o1.getDistance() > o2.getDistance())
                    return 1;
                else
                    return -1;
            }
        });


        requestShortDetails.clear();
        for (DistanceRequest distanceRequest : util.distanceRequestArrayList) {
            for (Request request : requests) {
                if (distanceRequest.getRequest_id().matches(request.getRequest_id() + "")) {
                    requestShortDetails.add(new RequestShortDetails(request.getRequest_id() + "", request.getTitle()
                            , R.mipmap.ic_launcher_round, request.getUser_name(),request.getLocation()
                            , (float) 1, request.isBookmarked()
                            , (int) request.getAmount_required(), request.getBackers(), request.getDays_left()
                            , (int) (request.getAmount_funded() * 100 / request.getAmount_required()), request.getViews()
                    ));
//                    Log.d(TAG, util.requestsNearYou.get(util.requestsNearYou.size() - 1).getTitle());
                }
            }
        }

    adapter.notifyDataSetChanged();

    }
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        final DatabaseReference myRef = database.getReference(util.path_base_path + util.path_requests);
//
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//
//
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    Double lat = Double.parseDouble(dataSnapshot1.child("lat").getValue().toString());
//                    Double lon = Double.parseDouble(dataSnapshot1.child("lon").getValue().toString());
//                    double dist = util.kilometerDistanceBetweenPoints(yourLocation.getLatitude(), yourLocation.getLongitude(), lat, lon);
//                    util.distanceRequestArrayList.add(new DistanceRequest(dataSnapshot1.child("request_id").getValue().toString(), dist));
//                    Log.d(TAG, dist + "");
//                }
//
//                myRef.removeEventListener(this);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
//
//        util.distanceRequestArrayList.sort(new Comparator<DistanceRequest>() {
//            @Override
//            public int compare(DistanceRequest o1, DistanceRequest o2) {
//                if (o1.getDistance() == o2.getDistance())
//                    return 0;
//                else if (o1.getDistance() > o2.getDistance())
//                    return 1;
//                else
//                    return -1;
//            }
//        });
//
//        util.requestsNearYou.clear();
//        final DatabaseReference myRef1 = database.getReference(util.path_base_path + util.path_requests);
//        Log.d(TAG, "test");
//        myRef1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    String request_id = dataSnapshot1.child("request_id").toString();
//                    for (final DistanceRequest distanceRequest : util.distanceRequestArrayList) {
//                        if (request_id.matches(distanceRequest.getRequest_id())) {
//                            util.requestsNearYou.add(dataSnapshot1.getValue(Request.class));
//                            Log.d(TAG, util.requestsNearYou.get(util.requestsNearYou.size() -1 ).getTitle());
//                        }
//                    }
//                }
//
//                myRef.removeEventListener(this);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
//
//
//        requestShortDetails.clear();
//        for (Request request : util.requestsNearYou) {
//            requestShortDetails.add( new RequestShortDetails(request.getRequest_id() + "", request.getTitle()
//                    , R.mipmap.ic_launcher_round, request.getUser_name(), "surathkal", (float)1, false
//                    , (int) request.getAmount_required(), request.getBackers(), request.getDays_left()
//                    , (int) (request.getAmount_funded() * 100 / request.getAmount_required()),request.getViews()
//            ));
//        }
//        adapter.notifyDataSetChanged();
//    }
}

