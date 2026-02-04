package org.halkKatilim.constant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class ElementInfo {

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("androidValue")
    @Expose
    private String androidValue;
    @SerializedName("androidType")
    @Expose
    private String androidType;
    @SerializedName("androidIndex")
    @Expose
    private int androidIndex;
    @SerializedName("iosValue")
    @Expose
    private String iosValue;
    @SerializedName("iosType")
    @Expose
    private String iosType;
    @SerializedName("iosIndex")
    @Expose
    private int iosIndex;

}
