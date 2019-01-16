package com.lwang.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author lwang
 * @date 2019/01/16
 * @description
 */
public class LuckyPanelItemView extends RelativeLayout {

    private static final int[] mAttr = {R.attr.prizeImg, R.attr.prizeName};
    private static final int ATTR_PRIZE_IMAGE = 0;
    private static final int ATTR_PRIZE_NAME = 1;

    private View itemBg;
    private View overlay;
    private TextView tvName;
    private ImageView imagePic;

    public LuckyPanelItemView(Context context) {
        this(context, null);
    }

    public LuckyPanelItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuckyPanelItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_panel_item, this);

        itemBg = findViewById(R.id.item_bg);
        overlay = findViewById(R.id.overlay);
        tvName = (TextView) findViewById(R.id.item_name);
        imagePic = (ImageView) findViewById(R.id.item_image);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LuckyPanelView);
        Drawable item = typedArray.getDrawable(R.styleable.LuckyPanelView_prizeImg);
        if (item != null) {
            itemBg.setBackground(item);
        }
        Drawable image = typedArray.getDrawable(R.styleable.LuckyPanelView_prizeImg);
        if (image != null) {
            imagePic.setImageDrawable(image);
        }
        String name = typedArray.getString(R.styleable.LuckyPanelView_prizeName);
        if (name != null) {
            tvName.setText(name);
        }

        typedArray.recycle();
    }


}
