package com.fyber.naveedahmad.androidfyberchallenge.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Naveed on 22/09/15
 */
public class ErrorModel {
    @SerializedName("code")
    private String mCode;
    @SerializedName("message")
    private String mErrorDetails;

    public ErrorModel(String code, String errorMessage) {
        mCode = code;
        mErrorDetails = errorMessage;
    }

    public String getCode() {
        return mCode;
    }

    public String getErrorDetais() {
        return mErrorDetails;
    }
}
