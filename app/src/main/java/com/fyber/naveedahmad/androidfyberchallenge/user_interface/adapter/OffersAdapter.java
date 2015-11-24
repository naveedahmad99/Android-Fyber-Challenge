package com.fyber.naveedahmad.androidfyberchallenge.user_interface.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.fyber.naveedahmad.androidfyberchallenge.model.Offer;
import com.fyber.naveedahmad.androidfyberchallenge.user_interface.view.OfferView;

/**
 * Created by Naveed on 22/09/15
 */
public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    private List<Offer> mOffers;
    private RecyclerViewListener mRecyclerViewListener;

    public OffersAdapter(RecyclerViewListener recyclerViewListener) {
        this.mRecyclerViewListener = recyclerViewListener;
        this.mOffers = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OfferView view = new OfferView(parent.getContext());
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Offer offer = mOffers.get(position);
        holder.mOfferView.fill(offer.getTitle(), offer.getLowresThumbnailUrl());
    }

    public void addItem(Offer offer) {
        this.mOffers.add(offer);
        notifyItemInserted(getItemCount());
    }

    public void addItemList(List<Offer> offers) {
        this.mOffers.addAll(offers);
        notifyDataSetChanged();
    }

    public void removeAll() {
        this.mOffers.clear();
        notifyDataSetChanged();
    }

    public void removeItemAt(int position) {
        this.mOffers.remove(position);
        notifyItemRemoved(position);
    }

    public Offer getItemAtPosition(int position) {
        return this.mOffers.get(position);
    }

    @Override
    public int getItemCount() {
        return this.mOffers.size();
    }

    public interface RecyclerViewListener {
        void onItemClickListener(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OfferView mOfferView;

        public ViewHolder(OfferView itemView) {
            super(itemView);
            this.mOfferView = itemView;
            this.mOfferView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mRecyclerViewListener.onItemClickListener(v, getAdapterPosition());
        }
    }
}

