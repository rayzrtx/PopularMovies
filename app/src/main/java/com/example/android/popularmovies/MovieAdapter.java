package com.example.android.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context mContext;
    private List<Movie> mMovie;
    private final MovieItemClickListener mOnClickListener;

    public interface MovieItemClickListener {
        void onMovieItemClick(int clickedItemIndex);
    }

    public MovieAdapter(Context mContext, List<Movie> mMovie, MovieItemClickListener listener) {

        this.mContext = mContext;
        this.mMovie = mMovie;
        mOnClickListener = listener;
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

    //Will bind the parsed JSON data to the correct views
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        //Retrieve movie title from parsed JSON and set title to the correct view
        holder.movieTitleTV.setText(mMovie.get(position).getMovieTitle());

        //Retrieve poster image url from parsed JSON and use Picasso library to set image
        Picasso.get()
                .load(mMovie.get(position).getPosterImageUrl())
                .into(holder.moviePosterIV);
    }

    void setFavorites(List<Movie> favorites){
        mMovie = favorites;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mMovie.size();
    }


    //Setting click listener for each RecyclerView item
    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView movieTitleTV;
        ImageView moviePosterIV;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieTitleTV = itemView.findViewById(R.id.movie_title_tv);
            moviePosterIV = itemView.findViewById(R.id.movie_poster_image_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mOnClickListener.onMovieItemClick(position);
        }
    }
}
