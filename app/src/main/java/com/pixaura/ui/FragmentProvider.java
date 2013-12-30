package com.pixaura.ui;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * Provides a fragment
 */
public interface FragmentProvider {

    /**
     * Get selected fragment
     *
     * @return fragment
     */
    SherlockFragment getSelected();
}
