package com.lwang.luckyturntable;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.concurrent.TimeUnit;

public class Main2Activity extends AppCompatActivity {

    private View springMenuBg;
    private RelativeLayout rlAddPost;
    private FloatingActionMenu springMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        springMenuBg = findViewById(R.id.spring_menu_bg);
        rlAddPost = (RelativeLayout) findViewById(R.id.rl_add);

        initFloatingActionsMenu(springMenuBg);
        initFloatingMenu();

        rlAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                springMenuBg.setVisibility(View.VISIBLE);
                Toast.makeText(Main2Activity.this,"nihao",Toast.LENGTH_SHORT).show();
            }
        });

        springMenuBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (springMenu.isOpen()) {
                    springMenu.close(true);
                }
            }
        });
    }



    private void initFloatingMenu() {

        TextView a = new TextView(this);
        a.setCompoundDrawablePadding(20);
        a.setGravity(Gravity.CENTER_HORIZONTAL);
        Drawable image = getResources().getDrawable(R.mipmap.spring_text);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        a.setCompoundDrawables(null, image, null, null);
        a.setText(R.string.add_post_text);

        TextView b = new TextView(this);
        b.setCompoundDrawablePadding(20);
        b.setGravity(Gravity.CENTER_HORIZONTAL);
        image = getResources().getDrawable(R.mipmap.spring_single_pic);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        b.setCompoundDrawables(null, image, null, null);
        b.setText(R.string.add_post_single_pic);

        TextView c = new TextView(this);
        c.setCompoundDrawablePadding(20);
        c.setGravity(Gravity.CENTER_HORIZONTAL);
        image = getResources().getDrawable(R.mipmap.spring_multi_pic);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        c.setCompoundDrawables(null, image, null, null);
        c.setText(R.string.add_post_multi_pic);

        TextView e = new TextView(Main2Activity.this);
        e.setCompoundDrawablePadding(20);
        e.setGravity(Gravity.CENTER_HORIZONTAL);
        image = getResources().getDrawable(R.mipmap.spring_gif_pic);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        e.setCompoundDrawables(null, image, null, null);
        e.setText(R.string.add_post_gif_pic);

        TextView d = new TextView(Main2Activity.this);
        d.setCompoundDrawablePadding(20);
        d.setGravity(Gravity.CENTER_HORIZONTAL);
        image = getResources().getDrawable(R.mipmap.spring_vote);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        d.setCompoundDrawables(null, image, null, null);
        d.setText(R.string.add_post_vote);

        SubActionButton.Builder subBuilder = new SubActionButton.Builder(this);


        SubActionButton buttonQuit = subBuilder.setContentView(a).build();
        SubActionButton buttonPalette = subBuilder.setContentView(b).build();
        SubActionButton buttonTool = subBuilder.setContentView(c).build();
        SubActionButton buttonCamera = subBuilder.setContentView(d).build();
        SubActionButton buttond = subBuilder.setContentView(e).build();

//
//
//        FrameLayout.LayoutParams tvParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        a.setLayoutParams(tvParams);
//        b.setLayoutParams(tvParams);
//        c.setLayoutParams(tvParams);
//        d.setLayoutParams(tvParams);
//        e.setLayoutParams(tvParams);


        springMenu = new FloatingActionMenu.Builder(this)
                .setStartAngle(-135)
                .setEndAngle(-45)
                .setRadius(getResources().getDimensionPixelSize(R.dimen.margin150))
                .addSubActionView(buttonQuit)
                .addSubActionView(buttonPalette)
                .addSubActionView(buttonTool)
                .addSubActionView(buttonCamera)
                .addSubActionView(buttond)
                .attachTo(rlAddPost)
                .build();

        springMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu floatingActionMenu) {
                showSpringMenuBg(true);
            }

            @Override
            public void onMenuClosed(FloatingActionMenu floatingActionMenu) {
                hideSpringMenuBg(true);
            }
        });

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PostActivity.start(this, "3");
                springMenu.close(false);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PostActivity.start(this, "1");
                springMenu.close(false);
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PostActivity.start(this, "2");
                springMenu.close(false);
            }
        });
    }




    private void showSpringMenuBg(boolean animated) {

        Log.i("wang", "-----------[");
        if (animated) {
            AlphaAnimation hideAnimation = new AlphaAnimation(0.0f, 1.0f);
            hideAnimation.setDuration(500);
            hideAnimation.setFillAfter(false);
            springMenuBg.startAnimation(hideAnimation);
        }
        springMenuBg.setVisibility(View.VISIBLE);
    }

    private void hideSpringMenuBg(boolean animated) {
        if (animated) {
            AlphaAnimation hideAnimation = new AlphaAnimation(1.0f, 0.0f);
            hideAnimation.setDuration(500);
            hideAnimation.setFillAfter(false);
            hideAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    springMenuBg.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            springMenuBg.startAnimation(hideAnimation);
        } else {
            springMenuBg.setVisibility(View.GONE);
        }
    }





    private void initFloatingActionsMenu(View view) {
        // 添加 右下角的白色+号按钮
        final ImageView fabIcon = new ImageView(this);
        fabIcon.setImageDrawable(getResources().getDrawable(R.mipmap.icon_add, null));
        final FloatingActionButton fabButton = new FloatingActionButton.Builder(this)
                .setContentView(fabIcon)
                .setPosition(FloatingActionButton.POSITION_BOTTOM_LEFT)
                .build();

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);

        ImageView imageViewQuit = new ImageView(this);
        ImageView imageViewTool = new ImageView(this);
        ImageView imageViewPalette = new ImageView(this);
        ImageView imageViewCamera = new ImageView(this);


        imageViewQuit.setImageDrawable(getResources().getDrawable(R.mipmap.spring_text, null));
        imageViewTool.setImageDrawable(getResources().getDrawable(R.mipmap.spring_single_pic, null));
        imageViewPalette.setImageDrawable(getResources().getDrawable(R.mipmap.spring_multi_pic, null));
        imageViewCamera.setImageDrawable(getResources().getDrawable(R.mipmap.spring_gif_pic, null));

        SubActionButton buttonQuit = rLSubBuilder.setContentView(imageViewQuit).build();
        SubActionButton buttonPalette = rLSubBuilder.setContentView(imageViewPalette).build();
        SubActionButton buttonTool = rLSubBuilder.setContentView(imageViewTool).build();
        SubActionButton buttonCamera = rLSubBuilder.setContentView(imageViewCamera).build();

        // Build the menu with default options: light theme, 90 degrees, 72dp
        // radius.
        // Set 4 default SubActionButtons
        // FloatingActionMenu通过attachTo(fabButton)附着到FloatingActionButton
        final FloatingActionMenu buttonToolMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonPalette)
                .addSubActionView(buttonCamera)
                .addSubActionView(buttonTool)
                .addSubActionView(buttonQuit)
                .setStartAngle(0)
                .setEndAngle(-90)
                .attachTo(fabButton)
                .build();

        // Listen menu open and close events to animate the button content view
        buttonToolMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // 增加按钮中的+号图标顺时针旋转45度
                // Rotate the icon of fabButton 45 degrees clockwise
                fabIcon.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIcon, pvhR);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // 增加按钮中的+号图标逆时针旋转45度
                // Rotate the icon of fabButton 45 degrees
                // counter-clockwise
                fabIcon.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIcon, pvhR);
                animation.start();
            }
        });

    }


}
