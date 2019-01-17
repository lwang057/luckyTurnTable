package com.lwang.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lwang
 * @date 2019/1/17
 * @description 可通过自定义属性设置、也可通过接口将图片和名字设置进来
 */
public class LuckyPanelView  extends FrameLayout {

    private ImageView imageBgOne;
    private ImageView imageBgTwo;
    private ImageView itemImageOne;
    private TextView itemNameOne;
    private ImageView itemImageTwo;
    private TextView itemNameTwo;
    private ImageView itemImageThree;
    private TextView itemNameThree;
    private ImageView itemImageFour;
    private TextView itemNameFour;
    private ImageView itemImageFive;
    private TextView itemNameFive;
    private ImageView itemImageSix;
    private TextView itemNameSix;
    private ImageView itemImageSeven;
    private TextView itemNameSeven;
    private ImageView itemImageEight;
    private TextView itemNameEight;
    private ItemView[] itemViewArr = new ItemView[8];
    private int[] image = new int[2];

    public LuckyPanelView(@NonNull Context context) {
        this(context, null);
    }

    public LuckyPanelView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuckyPanelView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(@Nullable Context context, @Nullable AttributeSet attrs) {
        inflate(context, R.layout.layout_lucky_panel_view, this);

        // 背景框
        imageBgOne = (ImageView) findViewById(R.id.image_view_bg_one);
        imageBgTwo = (ImageView) findViewById(R.id.image_view_bg_two);
        //item_1
        itemImageOne = (ImageView) findViewById(R.id.item_image_one);
        itemNameOne = (TextView) findViewById(R.id.item_name_one);
        //item_2
        itemImageTwo = (ImageView) findViewById(R.id.item_image_two);
        itemNameTwo = (TextView) findViewById(R.id.item_name_two);
        //item_3
        itemImageThree = (ImageView) findViewById(R.id.item_image_three);
        itemNameThree = (TextView) findViewById(R.id.item_name_three);
        //item_4
        itemImageFour = (ImageView) findViewById(R.id.item_image_four);
        itemNameFour = (TextView) findViewById(R.id.item_name_four);
        //item_5
        itemImageFive = (ImageView) findViewById(R.id.item_image_five);
        itemNameFive = (TextView) findViewById(R.id.item_name_five);
        //item_6
        itemImageSix = (ImageView) findViewById(R.id.item_image_six);
        itemNameSix = (TextView) findViewById(R.id.item_name_six);
        //item_7
        itemImageSeven = (ImageView) findViewById(R.id.item_image_seven);
        itemNameSeven = (TextView) findViewById(R.id.item_name_seven);
        //item_8
        itemImageEight = (ImageView) findViewById(R.id.item_image_eight);
        itemNameEight = (TextView) findViewById(R.id.item_name_eight);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LuckyPanelView);
        Drawable item = typedArray.getDrawable(R.styleable.LuckyPanelView_image_view_bg_one);
        Drawable item1 = typedArray.getDrawable(R.styleable.LuckyPanelView_image_view_bg_two);
        String name = typedArray.getString(R.styleable.LuckyPanelView_prizeName);

        typedArray.recycle();

        image[0] = R.mipmap.lucky_prize_bg_focused;
        image[1] = R.mipmap.lucky_prize_bg_normal;

        for (ItemView itemView : itemViewArr) {
            itemView.setFocus(false ,image);
        }

    }


}
