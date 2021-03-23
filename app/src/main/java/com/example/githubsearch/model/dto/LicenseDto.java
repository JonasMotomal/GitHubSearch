
package com.example.githubsearch.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LicenseDto {

    @SerializedName("key")
    public String key;

    @SerializedName("name")
    public String name;

    @SerializedName("spdx_id")
    public String spdxId;

    @SerializedName("url")
    public String url;

    @SerializedName("node_id")
    public String nodeId;

}
