package com.fyber.naveedahmad.androidfyberchallenge.tasks;

import android.content.Context;

import com.google.gson.Gson;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;

import javax.inject.Inject;

import com.fyber.naveedahmad.androidfyberchallenge.FyberChallengeApplication;
import com.fyber.naveedahmad.androidfyberchallenge.R;
import com.fyber.naveedahmad.androidfyberchallenge.model.ErrorModel;
import com.fyber.naveedahmad.androidfyberchallenge.model.OfferResponse;
import com.fyber.naveedahmad.androidfyberchallenge.network.client.FyberClient;
import com.fyber.naveedahmad.androidfyberchallenge.otto.OttoBus;
import com.fyber.naveedahmad.androidfyberchallenge.utils.StringUtils;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import roboguice.util.RoboAsyncTask;

/**
 * Created by Naveed on 22/09/15
 */
public class GetFyberOffersTask extends RoboAsyncTask<Response> {

    private static final String HASH_NAME = "X-Sponsorpay-Response-Signature";
    private final String mFormat;
    private final Integer mAppid;
    private final String mUid;
    private final String mLocale;
    private final String mOsVersion;
    private final Long mTimestamp;
    private final String mHashkey;
    private final String mGoogleAdId;
    private final Boolean mGoogleAdIdLimitedTrackingEnabled;
    private final String mIp;
    private final String mPub0;
    private final Integer mPage;
    private final String mOfferTypes;
    private final Long mPsTime;
    private final String mDevice;
    @Inject
    protected OttoBus ottoBus;
    @Inject
    private FyberClient mFyberClient;

    // first approach only accepts one custom param
    public GetFyberOffersTask(Context context,
                              String format,
                              Integer appid,
                              String uid,
                              String locale,
                              String osVersion,
                              Long timestamp,
                              String hashkey,
                              String googleAdId,
                              Boolean googleAdIdLimitedTrackingEnabled,
                              String ip,
                              String pub0,
                              Integer page,
                              String offerTypes,
                              Long psTime,
                              String device) {
        super(context);
        mFormat = format;
        mAppid = appid;
        mUid = uid;
        mLocale = locale;
        mOsVersion = osVersion;
        mTimestamp = timestamp;
        mHashkey = hashkey;
        mGoogleAdId = googleAdId;
        mGoogleAdIdLimitedTrackingEnabled = googleAdIdLimitedTrackingEnabled;
        mIp = ip;
        mPub0 = pub0;
        mPage = page;
        mOfferTypes = offerTypes;
        mPsTime = psTime;
        mDevice = device;
    }

    @Override
    public Response call() throws Exception {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        Response response = mFyberClient.getOffers(mFormat,
                mAppid,
                mUid,
                mLocale,
                mOsVersion,
                mTimestamp,
                mHashkey,
                mGoogleAdId,
                mGoogleAdIdLimitedTrackingEnabled,
                mIp,
                mPub0,
                mPage,
                mOfferTypes,
                mPsTime,
                mDevice
        );
        return response;
    }

    @Override
    protected void onSuccess(Response result) throws Exception {
        super.onSuccess(result);
        if (validateHash(result)) {
            GsonConverter converter = new GsonConverter(new Gson());
            OfferResponse offerResponse = (OfferResponse) converter.fromBody(result.getBody(), OfferResponse.class);
            if (!isCancelled()) {
                ottoBus.post(new Event(offerResponse));
            }
        } else {
            ottoBus.post(new NoValidHashEvent());
        }

    }

    private boolean validateHash(Response result) {
        String stringBody = "";
        try {
            stringBody = StringUtils.convertToString(result.getBody().in());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String hash = getHeader(HASH_NAME, result);
        String toHash = stringBody + FyberChallengeApplication.getApiKey();
        String sha1 = new String(Hex.encodeHex(DigestUtils.sha1(toHash)));
        return hash.equals(sha1);
    }

    private String getHeader(String hashName, Response result) {
        for (Header header : result.getHeaders()) {
            if (header.getName().equals(hashName)) {
                return header.getValue();
            }
        }
        return "";
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        if (e instanceof RetrofitError) {
            RetrofitError e1 = (RetrofitError) e;
            // Possible expected errors
            // 400: is for invalid parameter
            // 401: An invalid or missing has h key for this appid was given as a parameter in the request.
            // 500: An unknown error happened on the Fyber server.

            if (e1.getResponse() != null) {
                int status = e1.getResponse().getStatus();
                if (status == 400 || status == 401 || status == 500) {
                    ErrorModel fyberError = (ErrorModel) e1.getBodyAs(ErrorModel.class);
                    ottoBus.post(new FyberError(fyberError));
                } else {
                    ottoBus.post(new FyberError(String.valueOf(status), context.getString(R.string.unexpected_error)));
                }
            } else {
                super.onException(e);
                e.printStackTrace();
            }
        } else {
            super.onException(e);
        }
    }

    /*
         * Otto Event
    */
    public class Event {
        private OfferResponse mResult;

        public Event(OfferResponse result) {
            this.mResult = result;
        }

        public OfferResponse getResult() {
            return mResult;
        }
    }

    public class FyberError {

        private ErrorModel mErrorModel;

        public FyberError(ErrorModel fyberError) {
            mErrorModel = fyberError;
        }

        public FyberError(String code, String error) {
            mErrorModel = new ErrorModel(code, error);
        }

        public String getErrorMessage() {
            return mErrorModel.getErrorDetais();
        }

        public String getCode() {
            return mErrorModel.getCode();
        }
    }

    public class NoValidHashEvent extends FyberError {
        public NoValidHashEvent() {
            super(context.getString(R.string.no_valid_hash_title), context.getString(R.string.no_valid_hash_body));
        }
    }
}
