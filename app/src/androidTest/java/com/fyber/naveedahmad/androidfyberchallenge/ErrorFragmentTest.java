package com.fyber.naveedahmad.androidfyberchallenge;

import android.os.Bundle;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentTransaction;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fyber.naveedahmad.androidfyberchallenge.user_interface.activity.MainActivity;
import com.fyber.naveedahmad.androidfyberchallenge.user_interface.fragment.ErrorFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Naveed on 22/09/15
 */
@RunWith(AndroidJUnit4.class)
public class ErrorFragmentTest {
    private static final String ERROR_INVALID_APPID = "ERROR_INVALID_APPID";
    private static final String ERROR_MESSAGE = "An invalid application id (appid) was given as a parameter in the request";
    private static final String FYBER_ERROR_FRAGMENT_TAG = "tag";
    @Rule
    public final ActivityRule<MainActivity> main = new ActivityRule<>(MainActivity.class);

    private ErrorFragment startFragment() {
        FragmentTransaction startFragment = main.get().getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(ErrorFragment.CODE, ERROR_INVALID_APPID);
        bundle.putString(ErrorFragment.MESSAGE, ERROR_MESSAGE);
        ErrorFragment errorFragment = new ErrorFragment();
        errorFragment.setArguments(bundle);
        startFragment.addToBackStack(FYBER_ERROR_FRAGMENT_TAG);
        startFragment.replace(R.id.fragment, errorFragment, FYBER_ERROR_FRAGMENT_TAG);
        startFragment.commit();

        return errorFragment;
    }

    @Test
    public void testErrorFragment() {
        startFragment();
        onView(withText(ERROR_INVALID_APPID)).check(ViewAssertions.matches(isDisplayed()));
        onView(withText(ERROR_MESSAGE)).check(ViewAssertions.matches(isDisplayed()));
    }

}
