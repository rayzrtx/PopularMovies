package com.example.android.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context mContext;
    private ArrayList<Review> mReview;

    public ReviewAdapter(Context mContext, ArrayList<Review> mReview) {
        this.mContext = mContext;
        this.mReview = mReview;
    }

    //Will return the new view holder and inflate the review item layout
    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        boolean attachToRoot = false;
        int layoutForReviewItem = R.layout.movie_review_item;

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(layoutForReviewItem, parent, attachToRoot);
        ReviewViewHolder reviewViewHolder = new ReviewViewHolder(view);

        return reviewViewHolder;
    }

    //Will bind the parsed JSON data to the correct views
    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        //Retrieve Author name from JSON
        String authorName = mReview.get(position).getMovieReviewAuthor();

        //Retrieve Review content from JSON
        String reviewContent = mReview.get(position).getMovieReviewContent();

        //Set content in appropriate views
        holder.reviewAuthorName.setText(authorName);
        holder.reviewContent.setText(reviewContent);

    }


    @Override
    public int getItemCount() {
        return mReview.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView reviewAuthorName;
        TextView reviewContent;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            reviewAuthorName = itemView.findViewById(R.id.movie_review_author_name_tv);
            reviewContent = itemView.findViewById(R.id.movie_review_content_tv);
        }
    }
}
