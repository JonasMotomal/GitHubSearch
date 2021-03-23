
package com.example.githubsearch.model.dto;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class SearchDto {

    @SerializedName("total_count")
    public int totalCount;

    @SerializedName("incomplete_results")
    public boolean incompleteResults;

    @SerializedName("items")
    public List<ItemDto> itemsDto = null;

}
