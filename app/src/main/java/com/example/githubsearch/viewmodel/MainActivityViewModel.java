package com.example.githubsearch.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.githubsearch.model.Item;
import com.example.githubsearch.model.dto.ItemDto;
import com.example.githubsearch.model.dto.SearchDto;
import com.example.githubsearch.rest.IRepoSearchRestService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {

    private final static int PER_PAGE = 25;
    private int _currentPage;
    private String _query;
    private IRepoSearchRestService _repoSearchService;
    private boolean _hasAllDataLoaded;

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private MutableLiveData<List<Item>> _repoList;

    public LiveData<List<Item>> getRepoList() {
        if (_repoList == null) {
            _repoList = new MutableLiveData<>();
        }

        return _repoList;
    }

    private MutableLiveData<String> _errorMessage;

    public LiveData<String> getErrorMessage() {
        if (_errorMessage == null) {
            _errorMessage = new MutableLiveData<>();
        }

        return _errorMessage;
    }

    public void setDependencies(IRepoSearchRestService repoSearchService) {
        _repoSearchService = repoSearchService;
    }

    public void getRepoList(String query) {
        isLoading.setValue(true);
        _currentPage = 1;
        _query = query;
        _hasAllDataLoaded = false;
        Call<SearchDto> _call = _repoSearchService.getRepoList(query, PER_PAGE, _currentPage);
        _call.enqueue(new Callback<SearchDto>() {
            @Override
            public void onResponse(Call<SearchDto> call, Response<SearchDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (!response.body().itemsDto.isEmpty()) {
                        List<Item> repoList = new ArrayList<>();
                        for (ItemDto itemDto : response.body().itemsDto) {
                            repoList.add(new Item(itemDto));
                        }
                        _repoList.setValue(repoList);
                    } else {
                        _hasAllDataLoaded = true;
                        _errorMessage.setValue("No matching results");
                    }
                } else {
                    _errorMessage.setValue("Something went wrong");
                }

                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Call<SearchDto> call, Throwable t) {
                _errorMessage.setValue("Something went wrong");
                isLoading.setValue(false);
            }
        });
    }

    public void getNextPage() {
        if (isLoading != null && !isLoading.getValue() && !_hasAllDataLoaded) {
            _currentPage++;
            isLoading.setValue(true);
            Call<SearchDto> _call = _repoSearchService.getRepoList(_query, PER_PAGE, _currentPage);
            _call.enqueue(new Callback<SearchDto>() {
                @Override
                public void onResponse(Call<SearchDto> call, Response<SearchDto> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (!response.body().itemsDto.isEmpty()) {
                            List<Item> repoList = getRepoList().getValue();
                            for (ItemDto itemDto : response.body().itemsDto) {
                                repoList.add(new Item(itemDto));
                            }

                            _repoList.setValue(repoList);
                            isLoading.setValue(false);
                        } else {
                            _hasAllDataLoaded = true;
                        }
                    } else {
                        _errorMessage.setValue("Something went wrong");
                    }

                    isLoading.setValue(false);
                }

                @Override
                public void onFailure(Call<SearchDto> call, Throwable t) {
                    _errorMessage.setValue("Something went wrong");
                    isLoading.setValue(true);
                }
            });
        }
    }
}
