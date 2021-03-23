package com.example.githubsearch.dagger;

import com.example.githubsearch.rest.IRepoSearchRestService;
import com.example.githubsearch.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {RestServiceModule.class})
@Singleton
public interface MainComponent {

    void inject(MainActivity mainActivity);

    IRepoSearchRestService provideRepoSearchRestService();

}
