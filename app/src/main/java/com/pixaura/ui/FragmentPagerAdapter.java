package com.pixaura.ui;

import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * Pager adapter that provides the current fragment
 */
public abstract class FragmentPagerAdapter extends
        android.support.v4.app.FragmentPagerAdapter implements FragmentProvider {

    private final SherlockFragmentActivity activity;

    private SherlockFragment selected;

    /**
     * @param activity
     */
    public FragmentPagerAdapter(SherlockFragmentActivity activity) {
        super(activity.getSupportFragmentManager());

        this.activity = activity;
    }

    @Override
    public SherlockFragment getSelected() {
        return selected;
    }

    @Override
    public void setPrimaryItem(final ViewGroup container, final int position,
                               final Object object) {
        super.setPrimaryItem(container, position, object);

        boolean changed = false;
        if (object instanceof SherlockFragment) {
            changed = object != selected;
            selected = (SherlockFragment) object;
        } else {
            changed = object != null;
            selected = null;
        }

        if (changed)
            activity.invalidateOptionsMenu();
    }
}
