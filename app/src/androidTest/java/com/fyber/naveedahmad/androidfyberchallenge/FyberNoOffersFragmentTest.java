package com.fyber.naveedahmad.androidfyberchallenge;

import android.os.Bundle;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentTransaction;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fyber.naveedahmad.androidfyberchallenge.user_interface.activity.MainActivity;
import com.fyber.naveedahmad.androidfyberchallenge.user_interface.fragment.FyberNoOffersFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Naveed on 22/09/15
 */
@RunWith(AndroidJUnit4.class)
public class FyberNoOffersFragmentTest {
    private static final String ERROR_MESSAGE = "Successful request, but no offers are currently available for this user.";
    private static final String NO_OFFERS_FRAGMENT_TAG = "tag";
    @Rule
    public final ActivityRule<MainActivity> main = new ActivityRule<>(MainActivity.class);

    private FyberNoOffersFragment startFragment() {
        FragmentTransaction startFragment = main.get().getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(FyberNoOffersFragment.MESSAGE, ERROR_MESSAGE);
        FyberNoOffersFragment fyberNoOffersFragment = new FyberNoOffersFragment();
        fyberNoOffersFragment.setArguments(bundle);
        startFragment.addToBackStack(NO_OFFERS_FRAGMENT_TAG);
        startFragment.replace(R.id.fragment, fyberNoOffersFragment, NO_OFFERS_FRAGMENT_TAG);
        startFragment.commit();

        return fyberNoOffersFragment;
    }

    @Test
    public void testErrorFragment() {
        startFragment();
        onView(withText(ERROR_MESSAGE)).check(ViewAssertions.matches(isDisplayed()));
    }

}
