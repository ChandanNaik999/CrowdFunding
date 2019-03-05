package com.seproject.crowdfunder.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.adapter.HistoryAdapter;
import com.seproject.crowdfunder.adapter.nameAdapter;
import com.seproject.crowdfunder.models.Request;

import java.util.ArrayList;
import java.util.List;

public class DetailRequest extends AppCompatActivity {
    private List<Request> requestList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_request);

        nameAdapter adapter = new nameAdapter(requestList,this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        prepareDonorData();
    }

    private void prepareDonorData() {
        requestList.add(new Request("Rathan", "23-12-2018 ", "Rs. 2000"));
        requestList.add(new Request("Rahul", "23-12-2018 ", "Rs. 2000"));
        requestList.add(new Request("Venkat", "23-12-2018 ", "Rs. 2000"));
        requestList.add(new Request("Joy", "23-12-2018 ", "Rs. 2000"));
        requestList.add(new Request("Rathan", "23-12-2018 ", "Rs. 2000"));
        requestList.add(new Request("Rathan", "23-12-2018 ", "Rs. 2000"));
    }
}
