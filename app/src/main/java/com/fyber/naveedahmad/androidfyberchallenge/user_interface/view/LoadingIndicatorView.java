package com.fyber.naveedahmad.androidfyberchallenge.user_interface.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.inject.Inject;

import com.fyber.naveedahmad.androidfyberchallenge.R;

/**
 * Created by Naveed on 22/09/15
 */
public class LoadingIndicatorView extends FrameLayout {

    private ProgressBar progressBar;

    @Inject
    public LoadingIndicatorView(Context context) {
        super(context);
        init();
    }

    public LoadingIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.loading_indicator_view, this);

        progressBar = (ProgressBar) findViewById(R.id.view_loading_indicator_progress_bar);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(fetchThemeColor(), android.graphics.PorterDuff.Mode.SRC_ATOP);
        setBackgroundColor(fetchThemeBackgroundColor());
    }

    private int fetchThemeColor() {
        TypedValue typedValue = new TypedValue();

        TypedArray a = getContext().obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimary});
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }

    private int fetchThemeBackgroundColor() {
        TypedValue typedValue = new TypedValue();

        TypedArray a = getContext().obtainStyledAttributes(typedValue.data, new int[]{android.R.attr.windowBackground});
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }

    public void attach(final ViewGroup viewGroup) {
        final FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);
        if (viewGroup instanceof RelativeLayout || viewGroup instanceof FrameLayout) {
            viewGroup.addView(this, layoutParams);

        } else if (viewGroup instanceof LinearLayout) {
            viewGroup.addView(this, 0, layoutParams);
        }

        setVisibility(GONE);
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public boolean isShowing() {
        return getVisibility() == VISIBLE;
    }

    public void dismiss() {
        setVisibility(GONE);
    }
}

