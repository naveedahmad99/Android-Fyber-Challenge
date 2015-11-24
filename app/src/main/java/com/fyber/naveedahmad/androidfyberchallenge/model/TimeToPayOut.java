package com.fyber.naveedahmad.androidfyberchallenge.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Naveed on 22/09/15
 */
public class TimeToPayOut implements Serializable {
    @SerializedName("amount")
    private int mAmount;
    @SerializedName("readable")
    private String mReadable;
}
