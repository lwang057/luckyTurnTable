package com.lwang.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lwang
 * @date 2019/1/17
 * @description 可通过自定义属性设置、也可通过接口将图片和名字设置进来
 */
public class LuckyPanelView extends FrameLayout {

    private static final int[] mAttr = {R.attr.item_text_size, R.attr.item_text_color, R.attr.item_bg};
    private static final int ATTR_ITEM_TEXT_SIZE = 0;
    private static final int ATTR_ITEM_TEXT_COLOR = 1;
    private static final int ATTR_ITEM_BG = 2;
    private ImageView imageBgOne, imageBgTwo, itemClick;
    private RelativeLayout itemOne, itemTwo, itemThree, itemFour, itemFive, itemSix, itemSeven, itemEight;
    private ImageView itemImageOne, itemImageTwo, itemImageThree, itemImageFour, itemImageFive, itemImageSix, itemImageSeven, itemImageEight;
    private TextView itemNameOne, itemNameTwo, itemNameThree, itemNameFour, itemNameFive, itemNameSix, itemNameSeven, itemNameEight;

    public LuckyPanelView(@NonNull Context context) {
        this(context, null);
    }

    public LuckyPanelView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuckyPanelView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
        initAttribute(context, attrs);
    }

    private void initView(@Nullable Context context, @Nullable AttributeSet attrs) {
        inflate(context, R.layout.layout_lucky_panel_view, this);

        // 背景框
        imageBgOne = (ImageView) findViewById(R.id.image_view_bg_one);
        imageBgTwo = (ImageView) findViewById(R.id.image_view_bg_two);
        // 点击按钮
        itemClick = (ImageView) findViewById(R.id.item_click);
        //item_1
        itemOne = (RelativeLayout) findViewById(R.id.item_one);
        itemImageOne = (ImageView) findViewById(R.id.item_image_one);
        itemNameOne = (TextView) findViewById(R.id.item_name_one);
        //item_2
        itemTwo = (RelativeLayout) findViewById(R.id.item_two);
        itemImageTwo = (ImageView) findViewById(R.id.item_image_two);
        itemNameTwo = (TextView) findViewById(R.id.item_name_two);
        //item_3
        itemThree = (RelativeLayout) findViewById(R.id.item_three);
        itemImageThree = (ImageView) findViewById(R.id.item_image_three);
        itemNameThree = (TextView) findViewById(R.id.item_name_three);
        //item_4
        itemFour = (RelativeLayout) findViewById(R.id.item_four);
        itemImageFour = (ImageView) findViewById(R.id.item_image_four);
        itemNameFour = (TextView) findViewById(R.id.item_name_four);
        //item_5
        itemFive = (RelativeLayout) findViewById(R.id.item_five);
        itemImageFive = (ImageView) findViewById(R.id.item_image_five);
        itemNameFive = (TextView) findViewById(R.id.item_name_five);
        //item_6
        itemSix = (RelativeLayout) findViewById(R.id.item_six);
        itemImageSix = (ImageView) findViewById(R.id.item_image_six);
        itemNameSix = (TextView) findViewById(R.id.item_name_six);
        //item_7
        itemSeven = (RelativeLayout) findViewById(R.id.item_seven);
        itemImageSeven = (ImageView) findViewById(R.id.item_image_seven);
        itemNameSeven = (TextView) findViewById(R.id.item_name_seven);
        //item_8
        itemEight = (RelativeLayout) findViewById(R.id.item_eight);
        itemImageEight = (ImageView) findViewById(R.id.item_image_eight);
        itemNameEight = (TextView) findViewById(R.id.item_name_eight);
    }

    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, mAttr);

        // 设置item的字体大小
        float size = ta.getDimension(ATTR_ITEM_TEXT_SIZE, (float) getResources().getDimension(R.dimen.sp_11));
        itemNameOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        itemNameTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        itemNameThree.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        itemNameFour.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        itemNameFive.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        itemNameSix.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        itemNameSeven.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        itemNameEight.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);

        // 设置item的字体颜色
        int color = ta.getColor(ATTR_ITEM_TEXT_COLOR, getResources().getColor(R.color.color_b99d79));
        itemNameOne.setTextColor(color);
        itemNameTwo.setTextColor(color);
        itemNameThree.setTextColor(color);
        itemNameFour.setTextColor(color);
        itemNameFive.setTextColor(color);
        itemNameSix.setTextColor(color);
        itemNameSeven.setTextColor(color);
        itemNameEight.setTextColor(color);

        // 设置item的背景图片
        Drawable itemBg = ta.getDrawable(ATTR_ITEM_BG);
        if (itemBg != null) {
            itemOne.setBackgroundDrawable(itemBg);
            itemTwo.setBackgroundDrawable(itemBg);
            itemThree.setBackgroundDrawable(itemBg);
            itemFour.setBackgroundDrawable(itemBg);
            itemFive.setBackgroundDrawable(itemBg);
            itemSix.setBackgroundDrawable(itemBg);
            itemSeven.setBackgroundDrawable(itemBg);
            itemEight.setBackgroundDrawable(itemBg);
        }
    }

    public void setImageBg(int[] image) throws Exception {
        if (image != null && image.length >= 2) {
            imageBgOne.setImageResource(image[0]);
            imageBgTwo.setImageResource(image[1]);
        } else {
            throw new Exception("请设置两个背景图");
        }
    }

    public void setItemImage(int[] itemImage) throws Exception {
        if (itemImage != null && itemImage.length >= 8) {
            itemImageOne.setImageDrawable(getResources().getDrawable(itemImage[0]));
            itemImageTwo.setImageDrawable(getResources().getDrawable(itemImage[1]));
            itemImageThree.setImageDrawable(getResources().getDrawable(itemImage[2]));
            itemImageFour.setImageDrawable(getResources().getDrawable(itemImage[3]));
            itemImageFive.setImageDrawable(getResources().getDrawable(itemImage[4]));
            itemImageSix.setImageDrawable(getResources().getDrawable(itemImage[5]));
            itemImageSeven.setImageDrawable(getResources().getDrawable(itemImage[6]));
            itemImageEight.setImageDrawable(getResources().getDrawable(itemImage[7]));
        } else {
            throw new Exception("请设置八个图标");
        }
    }

    public void setItemName(int[] itemName) throws Exception {
        if (itemName != null && itemName.length >= 8) {
            itemNameOne.setText(itemName[0]);
            itemNameTwo.setText(itemName[1]);
            itemNameThree.setText(itemName[2]);
            itemNameFour.setText(itemName[3]);
            itemNameFive.setText(itemName[4]);
            itemNameSix.setText(itemName[5]);
            itemNameSeven.setText(itemName[6]);
            itemNameEight.setText(itemName[7]);
        } else {
            throw new Exception("请设置八个名称");
        }
    }

    public void setItemData(List<Map<String, String>> list){


    }


}
