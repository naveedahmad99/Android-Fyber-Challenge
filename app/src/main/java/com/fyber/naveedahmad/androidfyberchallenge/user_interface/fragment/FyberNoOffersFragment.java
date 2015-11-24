package com.fyber.naveedahmad.androidfyberchallenge.user_interface.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fyber.naveedahmad.androidfyberchallenge.R;
import roboguice.inject.InjectView;

/**
 * Created by Naveed on 22/09/15
 */
public class FyberNoOffersFragment extends AbstractFragment<FyberNoOffersFragment.Callbacks> {
    public static final String MESSAGE = "response_message";
    private String mResponseMessage;

    @InjectView(R.id.fragment_no_offers_message)
    private TextView mNoOffersMessage;

    public FyberNoOffersFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mResponseMessage = getArguments().getString(MESSAGE);
        return inflater.inflate(R.layout.fragment_fyber_no_offers, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNoOffersMessage.setText(mResponseMessage);
    }

    public interface Callbacks {
    }
}
