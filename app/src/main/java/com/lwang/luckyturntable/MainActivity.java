package com.lwang.luckyturntable;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lwang.library.LuckyPanelView;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionMenu buttonToolMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLucky();
        initFloatingActionsMenu();
    }

    private void initFloatingActionsMenu() {

        // 添加 右下角的白色+号按钮
        final ImageView fabIcon = new ImageView(this);
        fabIcon.setImageResource(R.mipmap.icon_add);

        final FloatingActionButton fabButton = new FloatingActionButton.Builder(this)
                .setContentView(fabIcon)
                .setPosition(FloatingActionButton.POSITION_BOTTOM_CENTER)
                .build();


        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);

        ImageView imageViewCircle = new ImageView(this);
        ImageView imageViewSudoku = new ImageView(this);
        imageViewCircle.setImageResource(R.mipmap.spring_single_pic);
        imageViewSudoku.setImageResource(R.mipmap.spring_multi_pic);

        SubActionButton buttonCircle = rLSubBuilder.setContentView(imageViewCircle).build();
        SubActionButton buttonSudoku = rLSubBuilder.setContentView(imageViewSudoku).build();


        // FloatingActionMenu通过attachTo(fabButton)附着到FloatingActionButton
        //                .setStartAngle(0)
//                .setEndAngle(-90)
        buttonToolMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonCircle)
                .addSubActionView(buttonSudoku)
//                .setStartAngle(0)
//                .setEndAngle(-90)
                .setStartAngle(-135)
                .setEndAngle(-45)
                .attachTo(fabButton)
                .build();


        buttonToolMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {

                // 增加按钮中的+号图标顺时针旋转45度
                fabIcon.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIcon, pvhR);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {

                // 增加按钮中的+号图标逆时针旋转45度
                fabIcon.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIcon, pvhR);
                animation.start();
            }
        });


        imageViewCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CircleTurntableActivity.class));
                buttonToolMenu.close(false);
            }
        });

        imageViewSudoku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SudokuTurnTableActivity.class));
                buttonToolMenu.close(false);
            }
        });
    }


    private int[] imageBg = {R.mipmap.lucky_lights_one, R.mipmap.lucky_lights_two};
    private int[] imageImage = {R.mipmap.lucky_prize1, R.mipmap.lucky_prize6, R.mipmap.lucky_prize3, R.mipmap.lucky_prize5,
            R.mipmap.lucky_prize6, R.mipmap.lucky_prize6, R.mipmap.lucky_prize4, R.mipmap.lucky_prize2};
    private int[] imageName = {R.string.iphone_8, R.string.iqiyi_month_card, R.string.lucky_bead, R.string.baofeng_vr_glasses,
            R.string.iqiyi_month_card, R.string.iqiyi_month_card, R.string.xiaomi_scales, R.string.beats_headset};

    private void initLucky() {
        LuckyPanelView luckyPanelView = (LuckyPanelView) findViewById(R.id.lucky_panel_view);

        try {
            luckyPanelView.setImageBg(imageBg);
            luckyPanelView.setItemBg(R.mipmap.lucky_prize_bg_normal);
            luckyPanelView.setItemImage(imageImage);
            luckyPanelView.setItemName(imageName);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
