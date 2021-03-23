package com.example.githubsearch.dagger;

import com.example.githubsearch.rest.IRepoSearchRestService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RestServiceModule {

    @Provides
    @Singleton
    IRetrofitFactoryProvider provideRetrofitFactoryProvider() {
        return new RetrofitFactoryProvider();
    }

    @Provides
    IRepoSearchRestService provideRepoSearchRestService(IRetrofitFactoryProvider retrofitFactoryProvider) {
        return retrofitFactoryProvider.createService(IRepoSearchRestService.class);
    }
}
