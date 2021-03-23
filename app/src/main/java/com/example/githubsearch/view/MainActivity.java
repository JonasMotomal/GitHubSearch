package com.example.githubsearch.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubsearch.R;
import com.example.githubsearch.adapter.RepoListAdapter;
import com.example.githubsearch.dagger.MainComponentProvider;
import com.example.githubsearch.databinding.ActivityMainBinding;
import com.example.githubsearch.model.Item;
import com.example.githubsearch.rest.IRepoSearchRestService;
import com.example.githubsearch.utils.IOnItemClickListener;
import com.example.githubsearch.viewmodel.MainActivityViewModel;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    IRepoSearchRestService repoSearchRestService;

    private MainActivityViewModel _viewModel;
    private ActivityMainBinding _binding;
    private RepoListAdapter repoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MainComponentProvider) getApplication()).providesMainComponent().inject(this);

        _viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        _binding.setLifecycleOwner(this);
        _binding.setViewModel(_viewModel);

        RecyclerView recyclerView = _binding.rvRepoList;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        IOnItemClickListener onRepoClickListener = new IOnItemClickListener() {
            @Override
            public void itemClick(String url) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        };

        repoListAdapter = new RepoListAdapter(onRepoClickListener);
        recyclerView.setAdapter(repoListAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                int totalCount = (repoListAdapter.getItemCount() - 1);

                if (lastVisibleItemPosition == totalCount) {
                    _viewModel.getNextPage();
                }
            }
        });

        _viewModel.setDependencies(repoSearchRestService);

        _viewModel.getRepoList().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                repoListAdapter.setRepoList(items);
            }
        });

        _viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

        _binding.btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = _binding.etSearchRepo.getText().toString();
                _viewModel.getRepoList(searchQuery);
            }
        });

    }
}