package com.fyber.naveedahmad.androidfyberchallenge.user_interface.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.fyber.naveedahmad.androidfyberchallenge.R;
import com.fyber.naveedahmad.androidfyberchallenge.model.Offer;
import com.fyber.naveedahmad.androidfyberchallenge.user_interface.adapter.OffersAdapter;
import roboguice.inject.InjectView;

/**
 * Created by Naveed on 22/09/15
 */
public class OffersFragment extends AbstractFragment<OffersFragment.Callbacks> implements OffersAdapter.RecyclerViewListener {

    @InjectView(R.id.fragment_offers_recyclerview)
    private RecyclerView mOffersRecyclerView;
    private OffersAdapter mOffersAdapter;

    public OffersFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_offers, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupOffers();
    }

    private void setupOffers() {
        mOffersRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());

        mOffersRecyclerView.setLayoutManager(layoutManager);

        mOffersAdapter = new OffersAdapter(this);
        mOffersAdapter.addItemList(callbacks.getOffers());
        mOffersRecyclerView.setAdapter(mOffersAdapter);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        callbacks.showDetails(mOffersAdapter.getItemAtPosition(position));
    }

    public interface Callbacks {
        List<Offer> getOffers();

        void showDetails(Offer offer);
    }
}
