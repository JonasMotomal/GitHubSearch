
package com.example.githubsearch.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.example.githubsearch.model.dto.ItemDto;
import com.example.githubsearch.model.dto.SearchDto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Search implements Parcelable {

    private int totalCount;
    private boolean incompleteResults;
    private List<Item> items;

    public Search(SearchDto searchDto) {
        totalCount = searchDto.totalCount;
        incompleteResults = searchDto.incompleteResults;
        items = new ArrayList<>();
        for (ItemDto itemDto : searchDto.itemsDto) {
            items.add(new Item(itemDto));
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


    protected Search(Parcel in) {
        totalCount = in.readInt();
        incompleteResults = in.readByte() != 0x00;
        if (in.readByte() == 0x01) {
            items = new ArrayList<Item>();
            in.readList(items, Item.class.getClassLoader());
        } else {
            items = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(totalCount);
        dest.writeByte((byte) (incompleteResults ? 0x01 : 0x00));
        if (items == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(items);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Search> CREATOR = new Parcelable.Creator<Search>() {
        @Override
        public Search createFromParcel(Parcel in) {
            return new Search(in);
        }

        @Override
        public Search[] newArray(int size) {
            return new Search[size];
        }
    };
}
