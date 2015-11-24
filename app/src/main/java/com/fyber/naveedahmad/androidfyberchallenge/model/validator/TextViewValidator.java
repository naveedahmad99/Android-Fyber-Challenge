package com.fyber.naveedahmad.androidfyberchallenge.model.validator;

import android.widget.TextView;

/**
 * Created by Naveed on 22/09/15
 */
public class TextViewValidator {
    protected final TextView mTextView;
    protected final String mErrorMessage;

    public TextViewValidator(TextView textView, String errorMessage) {
        this.mTextView = textView;
        this.mErrorMessage = errorMessage;
    }

    public boolean validate() {
        if (!validateFieldIsNotEmpty()) {
            mTextView.setError(mErrorMessage);
            return false;
        }

        mTextView.setError(null);
        return true;
    }

    protected boolean validateFieldIsNotEmpty() {
        return mTextView.getText() != null && !mTextView.getText().toString().trim().isEmpty();
    }

}
