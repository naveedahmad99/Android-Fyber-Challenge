package com.fyber.naveedahmad.androidfyberchallenge.user_interface.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

import com.fyber.naveedahmad.androidfyberchallenge.R;
import com.fyber.naveedahmad.androidfyberchallenge.model.Offer;
import com.fyber.naveedahmad.androidfyberchallenge.model.OfferResponse;
import com.fyber.naveedahmad.androidfyberchallenge.tasks.GetFyberOffersTask;
import com.fyber.naveedahmad.androidfyberchallenge.user_interface.fragment.ErrorFragment;
import com.fyber.naveedahmad.androidfyberchallenge.user_interface.fragment.OffersFragment;
import com.fyber.naveedahmad.androidfyberchallenge.user_interface.fragment.MainRequestFormFragment;
import com.fyber.naveedahmad.androidfyberchallenge.user_interface.fragment.FyberNoOffersFragment;
import com.fyber.naveedahmad.androidfyberchallenge.user_interface.fragment.OfferDetailDialogFragment;
import roboguice.activity.RoboActionBarActivity;

public class MainActivity extends RoboActionBarActivity implements MainRequestFormFragment.Callbacks, FyberNoOffersFragment.Callbacks, ErrorFragment.Callbacks, OffersFragment.Callbacks {

    private static final String MAIN_FORM_FRAGMENT_TAG = "main_form_fragment";
    private static final String OK = "OK";
    private static final String NO_CONTENT = "NO_CONTENT";
    private static final String NO_OFFERS_FRAGMENT_TAG = "no_offers_fragment";
    private static final String FYBER_ERROR_FRAGMENT_TAG = "fyber_error_fragment";
    private static final String OFFERS_FRAGMENT_TAG = "fyber_offers_fragment";
    private static final String OFFER_DETAIL_DIALOG_TAG = "offer_detail_dialog";
    private static final String LAST_RESPONSE = "last_response";
    //
    protected OfferResponse mLastResponse;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mLastResponse = (OfferResponse) savedInstanceState.getSerializable(LAST_RESPONSE);
        }

        setContentView(R.layout.activity_main);
        if (getCurrentFragment() == null) {
            setMainFragment();
        }
    }

    private void setMainFragment() {
        mFragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction startFragment = mFragmentManager.beginTransaction();
        startFragment.add(R.id.fragment, new MainRequestFormFragment(), MAIN_FORM_FRAGMENT_TAG);
        startFragment.commit();
    }

    protected Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

    @Override
    public void onFormSubmittedSuccessfully(OfferResponse response) {
        if (response.getCode().equals(OK)) {
            showOffers(response);
        } else if (response.getCode().equals(NO_CONTENT)) {
            showNotOffersFragment(response);
        } else {
            // unexpected result, do something!
        }
    }

    @Override
    public void onFormSubmittedWithError(GetFyberOffersTask.FyberError error) {
        showErrorFragment(error.getCode(), error.getErrorMessage());
    }

    private void showErrorFragment(String code, String errorMessage) {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction startFragment = mFragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(ErrorFragment.CODE, code);
        bundle.putString(ErrorFragment.MESSAGE, errorMessage);
        ErrorFragment errorFragment = new ErrorFragment();
        errorFragment.setArguments(bundle);
        startFragment.addToBackStack(FYBER_ERROR_FRAGMENT_TAG);
        startFragment.replace(R.id.fragment, errorFragment, FYBER_ERROR_FRAGMENT_TAG);
        startFragment.commit();
    }

    private void showOffers(OfferResponse response) {
        mLastResponse = response;
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction startFragment = mFragmentManager.beginTransaction();
        OffersFragment offersFragment = new OffersFragment();
        startFragment.addToBackStack(OFFERS_FRAGMENT_TAG);
        startFragment.replace(R.id.fragment, offersFragment, OFFERS_FRAGMENT_TAG);
        startFragment.commit();
    }

    private void showNotOffersFragment(OfferResponse response) {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction startFragment = mFragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(FyberNoOffersFragment.MESSAGE, response.getMessage());
        FyberNoOffersFragment fyberNoOffersFragment = new FyberNoOffersFragment();
        fyberNoOffersFragment.setArguments(bundle);
        startFragment.addToBackStack(NO_OFFERS_FRAGMENT_TAG);
        startFragment.replace(R.id.fragment, fyberNoOffersFragment, NO_OFFERS_FRAGMENT_TAG);
        startFragment.commit();
    }

    @Override
    public List<Offer> getOffers() {
        return mLastResponse.getOffers();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mLastResponse != null) {
            outState.putSerializable(LAST_RESPONSE, mLastResponse);
        }
    }

    @Override
    public void showDetails(Offer offer) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        OfferDetailDialogFragment addParticipantDialog = new OfferDetailDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(OfferDetailDialogFragment.OFFER, offer);
        addParticipantDialog.setArguments(bundle);
        addParticipantDialog.show(fragmentManager, OFFER_DETAIL_DIALOG_TAG);
    }
}
