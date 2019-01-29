package com.lwang.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lwang
 * @date 2019/1/17
 * @description 可通过自定义属性设置、也可通过接口将图片和名字设置进来
 */
public class LuckyPanelView extends FrameLayout {

    private static final int[] mAttr = {R.attr.item_text_size, R.attr.item_text_color, R.attr.item_click,
            R.attr.item_bg_normal, R.attr.item_bg_focused, R.attr.border_bg_one, R.attr.border_bg_two};
    private static final int ATTR_ITEM_TEXT_SIZE = 0;
    private static final int ATTR_ITEM_TEXT_COLOR = 1;
    private static final int ATTR_ITEM_CLICK = 2;
    private static final int ATTR_ITEM_BG_NORMAL = 3;
    private static final int ATTR_ITEM_BG_FOCUSED = 4;
    private static final int ATTR_BORDER_BG_ONE = 5;
    private static final int ATTR_BORDER_BG_TWO = 6;
    private ImageView imageBgOne, imageBgTwo, itemClick;
    private RelativeLayout[] itemBgArr = new RelativeLayout[8];
    private TextView[] itemNameArr = new TextView[8];
    private ImageView[] itemImageArr = new ImageView[8];
    private Drawable itemBgNormal, itemBgFocused;

    private int currentIndex = 0;
    private int currentTotal = 0;
    private int stayIndex = 0;

    private boolean isMarqueeRunning = false;
    private boolean isGameRunning = false;
    private boolean isTryToStop = false;

    private static final int DEFAULT_SPEED = 300;
    private static final int MIN_SPEED = 80;
    private int currentSpeed = DEFAULT_SPEED;


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
        initListener();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startMarquee();
    }

    @Override
    protected void onDetachedFromWindow() {
        stopMarquee();
        super.onDetachedFromWindow();
    }

    private void initView(@Nullable Context context, @Nullable AttributeSet attrs) {
        inflate(context, R.layout.layout_lucky_panel_view, this);

        // 背景框
        imageBgOne = (ImageView) findViewById(R.id.image_view_bg_one);
        imageBgTwo = (ImageView) findViewById(R.id.image_view_bg_two);
        // 点击按钮
        itemClick = (ImageView) findViewById(R.id.item_click);
        //item_1
        itemBgArr[1] = (RelativeLayout) findViewById(R.id.item_one);
        itemNameArr[0] = (TextView) findViewById(R.id.item_name_one);
        itemImageArr[0] = (ImageView) findViewById(R.id.item_image_one);
        //item_2
        itemBgArr[2] = (RelativeLayout) findViewById(R.id.item_two);
        itemNameArr[1] = (TextView) findViewById(R.id.item_name_two);
        itemImageArr[1] = (ImageView) findViewById(R.id.item_image_two);
        //item_3
        itemBgArr[3] = (RelativeLayout) findViewById(R.id.item_three);
        itemNameArr[2] = (TextView) findViewById(R.id.item_name_three);
        itemImageArr[2] = (ImageView) findViewById(R.id.item_image_three);
        //item_4
        itemBgArr[0] = (RelativeLayout) findViewById(R.id.item_four);
        itemNameArr[3] = (TextView) findViewById(R.id.item_name_four);
        itemImageArr[3] = (ImageView) findViewById(R.id.item_image_four);
        //item_5
        itemBgArr[4] = (RelativeLayout) findViewById(R.id.item_five);
        itemNameArr[4] = (TextView) findViewById(R.id.item_name_five);
        itemImageArr[4] = (ImageView) findViewById(R.id.item_image_five);
        //item_6
        itemBgArr[7] = (RelativeLayout) findViewById(R.id.item_six);
        itemNameArr[5] = (TextView) findViewById(R.id.item_name_six);
        itemImageArr[5] = (ImageView) findViewById(R.id.item_image_six);
        //item_7
        itemBgArr[6] = (RelativeLayout) findViewById(R.id.item_seven);
        itemNameArr[6] = (TextView) findViewById(R.id.item_name_seven);
        itemImageArr[6] = (ImageView) findViewById(R.id.item_image_seven);
        //item_8
        itemBgArr[5] = (RelativeLayout) findViewById(R.id.item_eight);
        itemNameArr[7] = (TextView) findViewById(R.id.item_name_eight);
        itemImageArr[7] = (ImageView) findViewById(R.id.item_image_eight);
    }

    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, mAttr);

        // 设置item的字体大小
        float size = ta.getDimension(ATTR_ITEM_TEXT_SIZE, (float) getResources().getDimension(R.dimen.sp_11));
        for (TextView itemName : itemNameArr) {
            itemName.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }

        // 设置item的字体颜色
        int color = ta.getColor(ATTR_ITEM_TEXT_COLOR, Color.DKGRAY);
        for (TextView itemName : itemNameArr) {
            itemName.setTextColor(color);
        }

        // 设置中间位置，立即抽奖的图片
        Drawable item = ta.getDrawable(ATTR_ITEM_CLICK);
        if (item != null) {
            itemClick.setImageDrawable(item);
        }

        // 设置item的背景图片
        itemBgNormal = ta.getDrawable(ATTR_ITEM_BG_NORMAL);
        itemBgFocused = ta.getDrawable(ATTR_ITEM_BG_FOCUSED);
        if (itemBgNormal != null) {
            for (RelativeLayout itemBg : itemBgArr) {
                itemBg.setBackgroundDrawable(itemBgNormal);
            }
        }

        // 设置九宫格边框的背景图片
        Drawable borderBgOne = ta.getDrawable(ATTR_BORDER_BG_ONE);
        if (borderBgOne != null) {
            imageBgOne.setImageDrawable(borderBgOne);
        }
        Drawable borderBgTwo = ta.getDrawable(ATTR_BORDER_BG_TWO);
        if (borderBgTwo != null) {
            imageBgTwo.setImageDrawable(borderBgTwo);
        }
    }

    private void initListener() {
        itemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnStartClick != null) {
                    mOnStartClick.onStartClick();
                }
            }
        });
    }

    /**
     * 设置转盘中item的名称和图片
     *
     * @param list
     */
    public void setItemData(List<ItemDataBean> list) {
        if (list != null && list.size() >= 8) {
            for (int i = 0; i < list.size(); i++) {
                itemNameArr[i].setText(list.get(i).getName());
                itemImageArr[i].setImageDrawable(getResources().getDrawable(list.get(i).getImage()));
            }
        }
    }

    private void stopMarquee() {
        isMarqueeRunning = false;
        isGameRunning = false;
        isTryToStop = false;
    }

    private void startMarquee() {
        isMarqueeRunning = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isMarqueeRunning) {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    post(new Runnable() {
                        @Override
                        public void run() {
                            if (imageBgOne != null && imageBgTwo != null) {
                                if (VISIBLE == imageBgOne.getVisibility()) {
                                    imageBgOne.setVisibility(GONE);
                                    imageBgTwo.setVisibility(VISIBLE);
                                } else {
                                    imageBgOne.setVisibility(VISIBLE);
                                    imageBgTwo.setVisibility(GONE);
                                }
                            }
                        }
                    });
                }
            }
        }).start();
    }


    private long getInterruptTime() {
        currentTotal++;
        if (isTryToStop) {
            currentSpeed += 20;
            if (currentSpeed > DEFAULT_SPEED) {
                currentSpeed = DEFAULT_SPEED;
            }
        } else {
            if (currentTotal / itemBgArr.length > 0) {
                currentSpeed -= 100;
            }
            if (currentSpeed < MIN_SPEED) {
                currentSpeed = MIN_SPEED;
            }
        }
        return currentSpeed;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public void startGame() {
        isGameRunning = true;
        isTryToStop = false;
        currentSpeed = DEFAULT_SPEED;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isGameRunning) {
                    try {
                        Thread.sleep(getInterruptTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    post(new Runnable() {
                        @Override
                        public void run() {
                            int preIndex = currentIndex;
                            currentIndex++;
                            if (currentIndex >= itemBgArr.length) {
                                currentIndex = 0;
                            }

                            itemBgArr[preIndex].setBackgroundDrawable(itemBgNormal);
                            itemBgArr[currentIndex].setBackgroundDrawable(itemBgFocused);

                            if (isTryToStop && currentSpeed == DEFAULT_SPEED && stayIndex == currentIndex) {
                                isGameRunning = false;
                                if (mListener != null) {
                                    mListener.onAnimationEnd();
                                }
                            }
                        }
                    });
                }
            }
        }).start();
    }

    public void tryToStop(int position) {
        stayIndex = position;
        isTryToStop = true;
    }

    /**
     * 重置转盘
     */
    public void reset() {
        isGameRunning = false;
        isTryToStop = false;
        isMarqueeRunning = true;
        currentIndex = 0;
        currentTotal = 0;
        stayIndex = 0;
        currentSpeed = DEFAULT_SPEED;
        mListener = null;

        for (RelativeLayout itemBg : itemBgArr) {
            itemBg.setBackgroundDrawable(itemBgNormal);
        }
    }


    LuckyMonkeyAnimationListener mListener;

    public void setGameListener(LuckyMonkeyAnimationListener listener) {
        mListener = listener;
    }

    public interface LuckyMonkeyAnimationListener {
        void onAnimationEnd();
    }


    OnStartClickListener mOnStartClick;

    public interface OnStartClickListener {
        void onStartClick();
    }

    /**
     * 设置点击立即抽奖的按钮
     *
     * @param onStartClick
     */
    public void setOnStartClickListener(OnStartClickListener onStartClick) {
        mOnStartClick = onStartClick;
    }

}
