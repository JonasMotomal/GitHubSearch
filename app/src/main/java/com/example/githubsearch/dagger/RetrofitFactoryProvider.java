package com.example.githubsearch.dagger;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactoryProvider implements IRetrofitFactoryProvider {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    @Override
    public <T> T createService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
