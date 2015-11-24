package com.fyber.naveedahmad.androidfyberchallenge.user_interface.fragment;

import android.content.Context;
import android.os.Bundle;

import roboguice.fragment.RoboFragment;

/**
 * Created by Naveed on 22/09/15
 */
public class AbstractFragment<T> extends RoboFragment {
    protected T callbacks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callbacks = (T) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() + " must implement Callback interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.callbacks = null;
    }
}

