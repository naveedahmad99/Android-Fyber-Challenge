package com.fyber.naveedahmad.androidfyberchallenge.user_interface.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.fyber.naveedahmad.androidfyberchallenge.R;
import com.fyber.naveedahmad.androidfyberchallenge.utils.ViewUtils;
import roboguice.inject.InjectView;

/**
 * Created by Naveed on 22/09/15
 */
public class OfferView extends LinearLayout {
    @InjectView(R.id.view_offer_icon)
    private ImageView mIcon;

    @InjectView(R.id.view_offer_description)
    private TextView mDescription;

    public OfferView(Context context) {
        super(context);
        init();
    }

    public OfferView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OfferView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.offer_view, this);
        ViewUtils.reallyInjectViews(this);
    }

    public void fill(String text, String url) {
        mDescription.setText(text);

        int size = getResources().getDimensionPixelSize(R.dimen.icon_size);

        Picasso.with(getContext())
                .load(url)
                .centerCrop()
                .resize(size, size)
                .placeholder(R.drawable.ic_send)
                .into(mIcon);
    }
}
