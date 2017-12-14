package com.lwang.luckyturntable;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

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

        initFloatingMenu();



        rlAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                springMenuBg.setVisibility(View.VISIBLE);
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

        FrameLayout.LayoutParams tvParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        a.setLayoutParams(tvParams);
        b.setLayoutParams(tvParams);
        c.setLayoutParams(tvParams);

        springMenu = new FloatingActionMenu.Builder(this)
                .setStartAngle(-135)
                .setEndAngle(-45)
                .setRadius(getResources().getDimensionPixelSize(R.dimen.margin150))
                .addSubActionView(a)
                .addSubActionView(b)
                .addSubActionView(c)
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



}
