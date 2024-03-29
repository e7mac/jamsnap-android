package com.pixaura.ui;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * {@link ViewPager} extension with support for horizontally scrolling an
 * embedded {@link WebView}
 */
public class ViewPager extends android.support.v4.view.ViewPager {

    /**
     * @param context
     */
    public ViewPager(final Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public ViewPager(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Set current item and return whether the item changed
     * <p>
     * This method does not call {@link #setCurrentItem(int)} unless the item
     * parameter differs from the current item
     *
     * @param item
     * @return true if set, false if same
     */
    public boolean setItem(final int item) {
        final boolean changed = item != getCurrentItem();
        if (changed)
            setCurrentItem(item, false);
        return changed;
    }

    /**
     * Set current item, invoke the listener if changes, and return whether the
     * item changed
     * <p>
     * This method does not call {@link #setCurrentItem(int)} unless the item
     * parameter differs from the current item
     *
     * @param item
     * @param listener
     * @return true if set, false if same
     */
    public boolean setItem(final int item, final OnPageChangeListener listener) {
        final boolean changed = setItem(item);
        if (changed && listener != null)
            listener.onPageSelected(item);
        return changed;
    }

    /**
     * Schedule a call to {@link #setItem(int)} to occur on the UI-thread
     *
     * @param item
     * @param listener
     */
    public void scheduleSetItem(final int item,
                                final OnPageChangeListener listener) {
        post(new Runnable() {

            @Override
            public void run() {
                setItem(item, listener);
            }
        });
    }

    /**
     * Schedule a call to {@link #setItem(int)} to occur on the UI-thread
     *
     * @param item
     */
    public void scheduleSetItem(final int item) {
        post(new Runnable() {

            @Override
            public void run() {
                setItem(item);
            }
        });
    }

    @Override
    protected boolean canScroll(final View v, final boolean checkV,
                                final int dx, final int x, final int y) {
        if (SDK_INT < ICE_CREAM_SANDWICH && v instanceof WebView)
            return ((WebView) v).canScrollHorizontally(-dx);
        else
            return super.canScroll(v, checkV, dx, x, y);
    }
}
