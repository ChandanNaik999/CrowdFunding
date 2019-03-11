package com.seproject.crowdfunder.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.models.RequestShortDetails;
import com.seproject.crowdfunder.ui.Distance;

import java.util.List;

public class RequestShortDetailsAdapter extends RecyclerView.Adapter<RequestShortDetailsAdapter.MyViewHolder> {

    private List<RequestShortDetails> requestList;
    private Context context;

    public RequestShortDetailsAdapter(Context mContext, List<RequestShortDetails> requestList) {
        this.requestList = requestList;
        this.context = mContext;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, name, location, amountRequested, backers, timeLeft;
        ImageView profilePic,bookmarked;
        RatingBar rating;
        ProgressBar percentFunded;
        CardView cardView;

        MyViewHolder(View view){
            super(view);
            title =  view.findViewById(R.id.title);
            name =  view.findViewById(R.id.name);
            location =  view.findViewById(R.id.location);
            amountRequested =  view.findViewById(R.id.amount_requested);
            backers =  view.findViewById(R.id.backers);
            timeLeft =  view.findViewById(R.id.time_left);
            rating =  view.findViewById(R.id.rating);
            profilePic =  view.findViewById(R.id.profilePic);
            bookmarked =  view.findViewById(R.id.bookmark);
            percentFunded = view.findViewById(R.id.percent_funded);
            cardView = view.findViewById(R.id.card);

        }




    }

    @NonNull
    @Override
    public RequestShortDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_request, parent, false);

        return new RequestShortDetailsAdapter.MyViewHolder(itemView);
    }



    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final RequestShortDetails request = requestList.get(position);
        holder.title.setText(request.getTitle());
        holder.name.setText(request.getName());
        holder.location.setText(request.getLocation());
        holder.profilePic.setImageResource(request.getProfilePic());
        holder.timeLeft.setText(String.format("%d\n days left", request.gettimeLeft()));
        holder.percentFunded.setProgress(request.getpercentFunded());
        holder.backers.setText(String.format("%d\ndonors", request.getBackers()));
        holder.amountRequested.setText(String.format("Rs.%d/-\n requested", request.getamountRequested()));
        holder.rating.setRating(request.getRating());
        if (request.isBookmarked()) {
            holder.bookmarked.setImageResource(R.drawable.ic_bookmarked);
        } else {
            holder.bookmarked.setImageResource(R.drawable.ic_not_bookmarked);
        }

        final ImageView bookmark = holder.bookmarked;
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (request.isBookmarked()){
                    bookmark.setImageResource(R.drawable.ic_not_bookmarked);
                    request.setBookmarked(false);
                }
                else {
                    bookmark.setImageResource(R.drawable.ic_bookmarked);
                    request.setBookmarked(true);
                }
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Distance.class));
            }
        });

    }
    @Override
    public int getItemCount() {
        return requestList.size();
    }
}
