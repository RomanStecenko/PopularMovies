package com.rst.popularmovies.api;

import java.io.IOException;

import retrofit2.Response;

public class MoviesApi {

    private static final String TAG = MoviesApi.class.getSimpleName();

    private static final String URL_BASE = "http://api.themoviedb.org/3/";

    private static MoviesService service = ServiceFactory.getService(MoviesService.class, URL_BASE);

    public static MoviesResponse getPopularMovies() throws IOException, BadResponseException {
            Response<MoviesResponse> response = service.getPopularMovies(PrivateApiKey.API_KEY).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new BadResponseException("Error: getPopularMovies() response, code: "
                        + response.code() + ", message: " + response.message());
            }
    }

    public static MoviesResponse getTopRatedMovies() throws IOException, BadResponseException {
        Response<MoviesResponse> response = service.getTopRatedMovies(PrivateApiKey.API_KEY).execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new BadResponseException("Error: getTopRatedMovies() response, code: "
                    + response.code() + ", message: " + response.message());
        }
    }
}
