package com.example.githubsearch.rest;

import com.example.githubsearch.model.dto.SearchDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IRepoSearchRestService {

    @GET("/search/repositories")
    Call<SearchDto> getRepoList(@Query("q") String query, @Query("per_page") int per_page, @Query("page") int page);
}
