package com.test.candidates.rest;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClientRx {

    @GET("api/?")
    Single<CandidateResponse> getCandidateData(@Query("results") String resultsAmount);

}
