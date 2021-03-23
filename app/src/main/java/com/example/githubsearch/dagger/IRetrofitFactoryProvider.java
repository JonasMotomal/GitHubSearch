package com.example.githubsearch.dagger;

public interface IRetrofitFactoryProvider {
    <T> T createService(Class<T> classToCreate);
}
