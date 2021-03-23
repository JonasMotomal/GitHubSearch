package com.example.githubsearch.dagger;

import android.app.Application;

public class RepoSearchApplication extends Application implements MainComponentProvider {

    private static MainComponent mainComponent;

    @Override
    public MainComponent providesMainComponent() {
        if (mainComponent == null) {
            mainComponent = DaggerMainComponent
                    .builder()
                    .build();
        }

        return mainComponent;
    }
}
