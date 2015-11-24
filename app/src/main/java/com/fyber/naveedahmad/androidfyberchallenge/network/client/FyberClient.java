package com.fyber.naveedahmad.androidfyberchallenge.network.client;

import com.squareup.okhttp.OkHttpClient;

import com.fyber.naveedahmad.androidfyberchallenge.BuildConfig;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by Naveed on 22/09/15
 */
public class FyberClient {
    private RestAdapter restAdapter;
    private IFyberClient mFyberClient;

    public FyberClient() {
        if (restAdapter == null || mFyberClient == null) {
            OkHttpClient okHttpClient = new OkHttpClient();

            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(BuildConfig.HOST)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setClient(new OkClient(okHttpClient))
                    .build();

            mFyberClient = restAdapter.create(IFyberClient.class);
        }
    }

    public Response getOffers(String format, Integer appid, String uid, String locale, String osVersion, Long timestamp, String hashkey, String googleAdId, Boolean googleAdIdLimitedTrackingEnabled, String ip, String pub0, Integer page, String offerTypes, Long psTime, String device) {
        return mFyberClient.getOffers(format, appid, uid, locale, osVersion, timestamp, hashkey, googleAdId, googleAdIdLimitedTrackingEnabled, ip, pub0, page, offerTypes, psTime, device);
    }
}
