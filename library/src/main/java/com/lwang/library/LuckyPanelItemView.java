package com.lwang.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author lwang
 * @date 2019/01/16
 * @description
 */
public class LuckyPanelItemView extends RelativeLayout implements ItemView {

    private static final int[] mAttr = {R.attr.prizeImg, R.attr.prizeName};
    private static final int ATTR_PRIZE_IMAGE = 0;
    private static final int ATTR_PRIZE_NAME = 1;

    private RelativeLayout itemBg;
    private ImageView itemImage;
    private TextView itemName;

    public LuckyPanelItemView(Context context) {
        this(context, null);
    }

    public LuckyPanelItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuckyPanelItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttribute(context, attrs);
    }

    private void initView(Context context) {

        inflate(context, R.layout.layout_lucky_panel_item_view, this);
        itemBg = (RelativeLayout) findViewById(R.id.item_bg);
        itemImage = (ImageView) findViewById(R.id.item_image);
        itemName = (TextView) findViewById(R.id.item_name);
    }

    private void initAttribute(Context context, AttributeSet attrs) {

        TypedArray ta = context.obtainStyledAttributes(attrs, mAttr);
        Drawable image = ta.getDrawable(ATTR_PRIZE_IMAGE);
        if (image != null) {
            itemImage.setImageDrawable(image);
        }
        String name = ta.getString(ATTR_PRIZE_NAME);
        if (name != null) {
            itemName.setText(name);
        }
    }

    @Override
    public void setFocus(boolean isFocused, int[] image) {
        if (itemBg != null) {
            itemBg.setBackgroundResource(isFocused ? image[0] : image[1]);
        }
    }


}
