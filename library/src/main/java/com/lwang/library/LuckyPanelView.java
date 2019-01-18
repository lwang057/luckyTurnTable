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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lwang
 * @date 2019/1/17
 * @description 可通过自定义属性设置、也可通过接口将图片和名字设置进来
 */
public class LuckyPanelView extends FrameLayout {

    private ImageView imageBgOne, imageBgTwo;
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
    }

    private void initView(@Nullable Context context, @Nullable AttributeSet attrs) {
        inflate(context, R.layout.layout_lucky_panel_view, this);

        // 背景框
        imageBgOne = (ImageView) findViewById(R.id.image_view_bg_one);
        imageBgTwo = (ImageView) findViewById(R.id.image_view_bg_two);
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

    public void setImageBg(int[] image) throws Exception {
        if (image != null && image.length >= 2) {
            imageBgOne.setImageResource(image[0]);
            imageBgTwo.setImageResource(image[1]);
        } else {
            throw new Exception("请设置两个背景图");
        }
    }

    public void setItemBg(int image) {
        itemOne.setBackgroundResource(image);
        itemTwo.setBackgroundResource(image);
        itemThree.setBackgroundResource(image);
        itemFour.setBackgroundResource(image);
        itemFive.setBackgroundResource(image);
        itemSix.setBackgroundResource(image);
        itemSeven.setBackgroundResource(image);
        itemEight.setBackgroundResource(image);
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


}
