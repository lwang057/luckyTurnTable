package com.lwang.luckyturntable.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwang.luckyturntable.R;
import com.zhy.autolayout.AutoRelativeLayout;


public class LuckyMonkeyPanelItemView extends AutoRelativeLayout implements ItemView{

    private static final int[] mAttr = { R.attr.prizeImg, R.attr.prizeName };
    private static final int ATTR_PRIZE_IMAGE = 0;
    private static final int ATTR_PRIZE_NAME = 1;

    private View itemBg;
    private View overlay;
    private TextView tvName;
    private ImageView imagePic;

    public LuckyMonkeyPanelItemView(Context context) {
        this(context, null);
    }

    public LuckyMonkeyPanelItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuckyMonkeyPanelItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_panel_item, this);

        itemBg = findViewById(R.id.item_bg);
        overlay = findViewById(R.id.overlay);
        tvName = (TextView) findViewById(R.id.item_name);
        imagePic = (ImageView) findViewById(R.id.item_image);

        TypedArray ta = context.obtainStyledAttributes(attrs, mAttr);
        Drawable drawable = ta.getDrawable(ATTR_PRIZE_IMAGE);
        if (drawable != null) {
            imagePic.setImageDrawable(drawable);
        }
        String name = ta.getString(ATTR_PRIZE_NAME);
        if (name != null) {
            tvName.setText(name);
        }
    }

    @Override
    public void setFocus(boolean isFocused) {
//        if (overlay != null) {
//            overlay.setVisibility(isFocused ? INVISIBLE : VISIBLE);
//        }

        if (itemBg != null) {
            itemBg.setBackgroundResource(isFocused ? R.mipmap.lucky_prize_bg_focused : R.mipmap.lucky_prize_bg_normal);
        }
    }

}
