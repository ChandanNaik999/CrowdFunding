package com.seproject.crowdfunder.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.adapter.HistoryAdapter;
import com.seproject.crowdfunder.models.Request;

import java.util.ArrayList;
import java.util.List;

public class RequestHistoryActivity extends AppCompatActivity {

    private List<Request> requestList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HistoryAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        HistoryAdapter adapter = new HistoryAdapter(requestList,this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        prepareHistoryData();
    }

    private void prepareHistoryData() {
        requestList.add(new Request("Request for Rs. 100", "21-12-2018 to 13-01-2019", "Rs. 10000"));
        requestList.add(new Request("Request for Rs. 200", "21-12-2018 to 13-01-2019", "Rs. 20000"));
        requestList.add(new Request("Request for Rs. 300", "21-12-2018 to 13-01-2019", "Rs. 30000"));
        requestList.add(new Request("Request for Rs. 400", "21-12-2018 to 13-01-2019", "Rs. 40000"));
        requestList.add(new Request("Request for Rs. 500", "21-12-2018 to 13-01-2019", "Rs. 20000"));
        requestList.add(new Request("Request for Rs. 800", "21-12-2018 to 13-01-2019", "Rs. 10000"));
        requestList.add(new Request("Request for Rs. 100", "21-12-2018 to 13-01-2019", "Rs. 4000"));
    }
}
