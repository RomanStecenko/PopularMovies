package com.rst.popularmovies.api;

/**
 * Created by rstetsenko on 7/27/2016.
 */
public class BadResponseException extends Exception {

    public BadResponseException(String s) {
        super(s);
    }
}
