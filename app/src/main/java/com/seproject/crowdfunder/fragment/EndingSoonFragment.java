package com.seproject.crowdfunder.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.Chandan.Adapter.RequestShortDetailsAdapter;
import com.seproject.crowdfunder.models.RequestShortDetails;

import java.util.ArrayList;
import java.util.List;

public class EndingSoonFragment extends Fragment {
    RecyclerView recyclerViewList;
    private List<RequestShortDetails> requestShortDetails = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        prepareRequestList();
        recyclerViewList =  view.findViewById(R.id.list);
        RequestShortDetailsAdapter adapter= new RequestShortDetailsAdapter(getContext(), requestShortDetails);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewList.setLayoutManager(mLayoutManager);
        recyclerViewList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewList.setAdapter(adapter);

        return view;
    }

    public void prepareRequestList() {
        int i = 0;
        while (i < 10) {
            int progress = (int)((i+1)*4.5);
            requestShortDetails.add(new RequestShortDetails(""+i,"Request",R.mipmap.ic_launcher_round, "Name" ,"Location" , 5,false ,0 , 0, 0, progress));
            i++;
        }
    }
}
