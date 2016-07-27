package com.rst.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rst.popularmovies.api.Movie;

import java.util.List;

public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.ViewHolder> {

    private static final String URL_BASE_IMAGE = "http://image.tmdb.org/t/p/";
    public static final String[] SIZES = {"w92", "w154", "w185", "w342", "w500", "w780", "original"};

    private Context context;
    private List<Movie> movies;

    public MoviesRecyclerAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = movies.get(holder.getAdapterPosition());
        String posterPath = URL_BASE_IMAGE + SIZES[2] + "/"  + movie.getPosterPath();
        Glide.with(context).load(posterPath).into(holder.image);
        holder.title.setText(movie.getTitle());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;

        public ViewHolder(View container) {
            super(container);
            this.image = (ImageView) container.findViewById(R.id.movie_image);
            this.title = (TextView) container.findViewById(R.id.movie_title);
        }
    }
}
