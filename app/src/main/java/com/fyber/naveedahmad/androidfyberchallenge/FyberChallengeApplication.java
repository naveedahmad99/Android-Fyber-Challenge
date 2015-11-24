package com.fyber.naveedahmad.androidfyberchallenge;

import android.app.Application;

/**
 * Created by Naveed on 22/09/15
 */
public class FyberChallengeApplication extends Application {
    // hardcoded for now, got from challenge specification
    private static String API_KEY = "1c915e3b5d42d05136185030892fbb846c278927";

    public static String getApiKey() {
        return API_KEY;
    }
}
