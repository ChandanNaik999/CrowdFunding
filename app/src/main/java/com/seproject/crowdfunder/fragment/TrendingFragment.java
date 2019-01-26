package com.seproject.crowdfunder.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.adapter.RequestShortDetailsAdapter;
import com.seproject.crowdfunder.models.RequestShortDetails;

import java.util.ArrayList;
import java.util.List;

public class TrendingFragment extends Fragment {
    RecyclerView recylcerViewList;
    private List<RequestShortDetails> requestShortDetails = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        recylcerViewList = (RecyclerView) view.findViewById(R.id.list);
        RequestShortDetailsAdapter adapter= new RequestShortDetailsAdapter(getContext(), requestShortDetails);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recylcerViewList.setLayoutManager(mLayoutManager);
        recylcerViewList.setItemAnimator(new DefaultItemAnimator());
        recylcerViewList.setAdapter(adapter);

        return view;
    }

    public void prepareRequestList() {
        int i = 0;
        while (i < 10) {
            requestShortDetails.add(new RequestShortDetails());
            i++;
        }
    }
}