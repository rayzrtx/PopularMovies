package com.example.android.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context mContext;
    private List<Movie> mMovie;

    public MovieAdapter(Context mContext, List<Movie> mMovie) {

        this.mContext = mContext;
        this.mMovie = mMovie;
    }

    //Will return the new view holder and inflate the movie item layout
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        boolean attachToRoot = false;
        int layoutIdForMovieItem = R.layout.cardview_item_movie;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layoutIdForMovieItem, parent, attachToRoot);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.movieTitleTV.setText(mMovie.get(position).getMovieTitle());
        holder.moviePosterIV.setText(mMovie.get(position).getPosterImage());

    }

    @Override
    public int getItemCount() {
        return mMovie.size();
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder{
        TextView movieTitleTV;
        TextView moviePosterIV;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieTitleTV = itemView.findViewById(R.id.movie_title_tv);
            moviePosterIV = itemView.findViewById(R.id.movie_poster_image_iv);
        }
    }
}
