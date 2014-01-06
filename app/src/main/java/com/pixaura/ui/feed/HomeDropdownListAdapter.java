package com.pixaura.ui.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pixaura.R;
import com.pixaura.util.SingleTypeAdapter;
import com.pixaura.R.drawable;
import com.pixaura.R.id;
import com.pixaura.R.string;

import java.util.ArrayList;
import java.util.List;

/**
 * Dropdown list adapter to for navigation
 */
public class HomeDropdownListAdapter extends SingleTypeAdapter<Object> {

    public static final int ACTION_FEED = 0;
    public static final int ACTION_EXPLORE = 1;
    public static final int ACTION_ACTIVITY = 2;
    public static final int ACTION_PROFILE = 3;

    private int selected;

    private final LayoutInflater inflater;

    /**
     * Create adapter
     *
     * @param context
     */
    public HomeDropdownListAdapter(final Context context) {
        super(context, R.layout.dropdown_item);

        inflater = LayoutInflater.from(context);
        setOrgs();
    }

    /**
     * @param selected
     * @return this adapter
     */
    public HomeDropdownListAdapter setSelected(int selected) {
        this.selected = selected;
        return this;
    }

    /**
     * @return selected
     */
    public int getSelected() {
        return selected;
    }


    public HomeDropdownListAdapter setOrgs() {

        List<Object> all = new ArrayList<Object>(4);

        // Add dummy objects for feed, explore, activity, profile
        all.add(new Object());
        all.add(new Object());
        all.add(new Object());
        all.add(new Object());
        setItems(all);
        notifyDataSetChanged();
        return this;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { id.tv_dropdown, id.iv_dropdown };
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = initialize(inflater.inflate(R.layout.dropdown_item, null));
        update(position, convertView, getItem(position));
        return convertView;
    }

    private void setActionIcon(ImageView image, int drawable) {
        image.setImageResource(drawable);
        image.setTag(id.iv_dropdown, null);
    }

    @Override
    protected void update(int position, Object item) {
        switch (position) {
            case ACTION_EXPLORE:
                setText(0, string.title_explore);
                setActionIcon(imageView(1), drawable.ic_menu_explore);
                break;
            case ACTION_ACTIVITY:
                setText(0, string.title_activity);
                setActionIcon(imageView(1), drawable.ic_menu_activity);
                break;
            case ACTION_PROFILE:
                setText(0, string.title_profile);
                setActionIcon(imageView(1), drawable.ic_menu_profile);
                break;
            default:
                setText(0, string.title_feed);
                setActionIcon(imageView(1), drawable.ic_menu_feed);
        }
    }
}
