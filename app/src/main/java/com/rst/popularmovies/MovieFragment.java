package com.rst.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rst.popularmovies.api.BadResponseException;
import com.rst.popularmovies.api.MoviesApi;
import com.rst.popularmovies.api.MoviesResponse;

import java.io.IOException;

public class MovieFragment extends Fragment {

    public static final String ARG_MOVIES_TYPE = "ARG_MOVIES_TYPE";

    public static MovieFragment newInstance(MoviesTypes moviesTypes) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIES_TYPE, moviesTypes);
        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public enum MoviesTypes {
        POPULAR(R.string.movies_popular), TOP_RATED(R.string.movies_top_rated);

        private final int nameId;

        MoviesTypes(int nameId) {
            this.nameId = nameId;
        }

        public int getNameId() {
            return nameId;
        }
    }

    private RecyclerView recyclerView;
    private MoviesRecyclerAdapter adapter;
    private MoviesTypes moviesTypes;
    private AsyncTask<MoviesTypes, Void, Object> fetchMoviesTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moviesTypes = (MoviesTypes) getArguments().get(ARG_MOVIES_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchMovies();
    }

    @Override
    public void onDestroy() {
        if (fetchMoviesTask != null && (fetchMoviesTask.getStatus() == AsyncTask.Status.PENDING
                || fetchMoviesTask.getStatus() == AsyncTask.Status.RUNNING)) {
            fetchMoviesTask.cancel(true);
        }
        super.onDestroy();
    }

    private void fetchMovies() {
        fetchMoviesTask = new AsyncTask<MoviesTypes, Void, Object>() {
            @Override
            protected Object doInBackground(MoviesTypes... types) {
                try {
                    MoviesTypes type = types[0];
                    switch (type) {
                        case POPULAR:
                            return MoviesApi.getPopularMovies();
                        case TOP_RATED:
                            return MoviesApi.getTopRatedMovies();
                        default:
                            throw new RuntimeException("Unknown type of movies");
                    }
                } catch (IOException | BadResponseException e) {
                    return e;
                }
            }

            @Override
            protected void onPostExecute(Object result) {
                super.onPostExecute(result);
                if (result != null) {
                    if (result instanceof MoviesResponse) {
                        handleResult((MoviesResponse) result);
                    } else {
                        Log.e(MainActivity.TAG, "onPostExecute: ", ((Exception)result));
                    }
                } else {
                    Log.d(MainActivity.TAG, "onPostExecute: result == null");
                }
            }
        }.execute(moviesTypes);
    }

    private void handleResult(MoviesResponse result) {
        if (adapter == null) {
            adapter = new MoviesRecyclerAdapter(getActivity(), result.getResults());
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
