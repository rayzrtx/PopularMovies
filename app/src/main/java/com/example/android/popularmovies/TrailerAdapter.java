package com.example.android.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.data.URLConstant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private Context mContext;
    private ArrayList<Trailer> mTrailer;
    private final TrailerItemClickListener mOnClickListener;

    public interface TrailerItemClickListener{
        void onTrailerItemClick(int clickedItemIndex);
    }

    public TrailerAdapter (Context mContext, ArrayList<Trailer> mTrailer, TrailerItemClickListener listener){
        this.mContext = mContext;
        this.mTrailer = mTrailer;
        mOnClickListener = listener;
    }

    //Will return the new view holder and inflate the trailer item layout
    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        boolean attachToRoot = false;
        int layoutForTrailerItem = R.layout.movie_trailer_item;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layoutForTrailerItem, parent, attachToRoot);
        TrailerViewHolder trailerViewHolder = new TrailerViewHolder(view);
        return trailerViewHolder;
    }

    //Will bind the parsed JSON data to the correct views
    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        //Retrieve trailer key to get trailer image
        String trailerKey = mTrailer.get(position).getMovieTrailerKey();
        //URL to retrieve trailer thumbnail
        String trailerThumbnailURL = URLConstant.TRAILER_THUMBNAIL_BASE_URL + trailerKey + URLConstant.TRAILER_THUMBNAIL_URL_ENDING;
        //Loading thumbnail image into trailer image view
        Picasso.get()
                .load(trailerThumbnailURL)
                .into(holder.trailerThumbnailIV);

        holder.trailerTitleTV.setText(mTrailer.get(position).getMovieTrailerName());

    }

    @Override
    public int getItemCount() {
        return mTrailer.size();
    }

    //Setting click listener for each RecyclerView item
    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView trailerTitleTV;
        ImageView trailerThumbnailIV;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            trailerTitleTV = itemView.findViewById(R.id.movie_trailer_title_tv);
            trailerThumbnailIV = itemView.findViewById(R.id.movie_trailer_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mOnClickListener.onTrailerItemClick(position);

        }
    }
}
