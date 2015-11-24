package com.fyber.naveedahmad.androidfyberchallenge.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Naveed on 22/09/15
 */
public class Information implements Serializable {
    @SerializedName("app_name")
    private String mAppName;
    @SerializedName("appid")
    private String mAppId;
    @SerializedName("virtual_currency")
    private String mVirtualCurrency;

    // this field is not in the documentation but I can see it in the response
    @SerializedName("virtual_currency_sale_enabled")
    private Boolean mVirtualCurrencySaleEnabled;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("language")
    private String mLanguage;
    @SerializedName("support_url")
    private String mSupportUrl;
}
