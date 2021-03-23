
package com.example.githubsearch.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.githubsearch.model.dto.LicenseDto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class License implements Parcelable {

    private String key;
    private String name;
    private String spdxId;
    private String url;
    private String nodeId;

    public License(LicenseDto licenseDto) {
        key = licenseDto.key;
        name = licenseDto.name;
        spdxId = licenseDto.spdxId;
        url = licenseDto.url;
        nodeId = licenseDto.nodeId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpdxId() {
        return spdxId;
    }

    public void setSpdxId(String spdxId) {
        this.spdxId = spdxId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }


    protected License(Parcel in) {
        key = in.readString();
        name = in.readString();
        spdxId = in.readString();
        url = in.readString();
        nodeId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(spdxId);
        dest.writeString(url);
        dest.writeString(nodeId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<License> CREATOR = new Parcelable.Creator<License>() {
        @Override
        public License createFromParcel(Parcel in) {
            return new License(in);
        }

        @Override
        public License[] newArray(int size) {
            return new License[size];
        }
    };
}
