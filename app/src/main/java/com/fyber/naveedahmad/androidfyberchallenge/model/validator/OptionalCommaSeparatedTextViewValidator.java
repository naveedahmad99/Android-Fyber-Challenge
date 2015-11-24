package com.fyber.naveedahmad.androidfyberchallenge.model.validator;

import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by Naveed on 22/09/15
 */
public class OptionalCommaSeparatedTextViewValidator extends OptionalTextViewValidator {
    public OptionalCommaSeparatedTextViewValidator(CheckBox checkBox, TextView textView, String error) {
        super(checkBox, textView, error);
    }

    public boolean validate() {

        if (!isCommaSeparated()) {
            mTextView.setError(mErrorMessage);
            return false;
        }

        return super.validate();
    }

    private boolean isCommaSeparated() {
        // TODO: complete with validation accordingly
        return true;
    }
}
