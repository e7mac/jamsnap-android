package com.jamsnap.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jamsnap.R;
import com.jamsnap.model.Item;
import com.octo.android.robospice.spicelist.SpiceListItemView;

public class ItemView extends RelativeLayout implements SpiceListItemView<Item> {

    private TextView userNameTextView;
    private TextView githubContentTextView;
    private ImageView thumbImageView;
    private Item item;

    public ItemView(Context context) {
        super(context);
        inflateView(context);
    }

    private void inflateView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_cell_jamsnap, this);
        this.userNameTextView = (TextView) this.findViewById(R.id.user_name_textview);
        this.githubContentTextView = (TextView) this.findViewById(R.id.github_content_textview);
        this.thumbImageView = (ImageView) this.findViewById(R.id.octo_thumbnail_imageview);
    }

    @Override
    public void update(Item item) {
        this.item = item;
        userNameTextView.setText(item.user.username);
        githubContentTextView.setText(String.valueOf(item.caption));
    }

    @Override
    public Item getData() {
        return item;
    }

    @Override
    public ImageView getImageView(int imageIndex) {
        return thumbImageView;
    }

    @Override
    public int getImageViewCount() {
        return 1;
    }
}
