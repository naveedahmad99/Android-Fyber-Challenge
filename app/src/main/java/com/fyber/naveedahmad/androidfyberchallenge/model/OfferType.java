package com.fyber.naveedahmad.androidfyberchallenge.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Naveed on 22/09/15
 */
public class OfferType implements Serializable {
    @SerializedName("offer_type_id")
    private String mOfferTypeId;
    @SerializedName("readable")
    private String mReadable;
}
