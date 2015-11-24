package com.fyber.naveedahmad.androidfyberchallenge;

import android.content.Context;
import android.os.Bundle;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.fyber.naveedahmad.androidfyberchallenge.model.Offer;
import com.fyber.naveedahmad.androidfyberchallenge.model.OfferResponse;
import com.fyber.naveedahmad.androidfyberchallenge.model.Thumbnail;
import com.fyber.naveedahmad.androidfyberchallenge.user_interface.activity.MainActivity;
import com.fyber.naveedahmad.androidfyberchallenge.user_interface.fragment.OfferDetailDialogFragment;
import com.fyber.naveedahmad.androidfyberchallenge.user_interface.fragment.OffersFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Naveed on 22/09/15
 */
@RunWith(AndroidJUnit4.class)
public class OffersFragmentTest {

    private static final String OFFERS_FRAGMENT_TAG = "tag";
    private static final String LOWRES = "http://cdn2.sponsorpay.com/assets/56333/Less-or-more_square_60.PNG";
    private static final String HIRES = "http://cdn2.sponsorpay.com/assets/56333/Less-or-more_square_175.PNG";
    private static final String TITLE = "Washing Colors";
    private static final String OFFER_DETAIL_DIALOG_TAG = "dialog_tag";
    private static final String TEASER = "Complete the level 1 by reaching 33 points";
    private static final String PAYOUT = "100";
    @Rule
    public final ActivityRule<MainActivity> main = new ActivityRule<>(MainActivity.class);

    private OffersFragment startFragment() {
        FragmentTransaction startFragment = main.get().getSupportFragmentManager().beginTransaction();
        FakeOffersFragment fyberOffersFragment = new FakeOffersFragment();
        startFragment.replace(R.id.fragment, fyberOffersFragment, OFFERS_FRAGMENT_TAG);
        startFragment.commit();

        return fyberOffersFragment;
    }

    @Test
    public void testOffersFragment() {
        startFragment();
        // assert that recycler view and offer title, thumbnail are displayed
        onView(withId(R.id.fragment_offers_recyclerview)).check(ViewAssertions.matches(isDisplayed()));
        onView(withText(TITLE)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.view_offer_icon)).check(ViewAssertions.matches(isDisplayed()));

        // click on first offer
        onView(withId(R.id.fragment_offers_recyclerview)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // check that dialog, title, teaser, payout and thumbnail are displayed
        onView(withText(TITLE)).check(ViewAssertions.matches(isDisplayed()));
        onView(withText(TEASER)).check(ViewAssertions.matches(isDisplayed()));
        onView(withText(PAYOUT)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.fragment_offer_details_icon)).check(ViewAssertions.matches(isDisplayed()));
    }

    public class FakeOffersFragment extends OffersFragment {
        public FakeOffersFragment() {

        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            this.callbacks = new FakeMainActivity();
        }
    }

    private class FakeMainActivity extends MainActivity {
        public FakeMainActivity() {
            mLastResponse = new OfferResponse();
        }

        @Override
        public List<Offer> getOffers() {
            List<Offer> offers = new ArrayList<>();
            Offer offer = new Offer();
            offer.setTitle(TITLE);
            offer.setTeaser(TEASER);
            offer.setPayout(PAYOUT);
            Thumbnail thumbnail = new Thumbnail();
            thumbnail.setLowres(LOWRES);
            thumbnail.setHires(HIRES);
            offer.setThumbnail(thumbnail);
            offers.add(offer);
            return offers;
        }

        @Override
        public void showDetails(Offer offer) {
            FragmentManager fragmentManager = main.get().getSupportFragmentManager();
            OfferDetailDialogFragment addParticipantDialog = new OfferDetailDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(OfferDetailDialogFragment.OFFER, offer);
            addParticipantDialog.setArguments(bundle);
            addParticipantDialog.show(fragmentManager, OFFER_DETAIL_DIALOG_TAG);
        }
    }
}
