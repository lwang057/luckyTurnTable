package com.lwang.luckyturntable;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.lwang.library.ItemDataBean;
import com.lwang.library.LuckyPanelView;
import com.lwang.luckyturntable.view.LuckyMonkeyPanelView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author lwang
 * @date 2019/01/29
 * @description
 */
public class TestActivity extends AppCompatActivity {

    private List<ItemDataBean> list = new ArrayList<>();
    private long drawTime; // 点击立即抽奖的间隔时间
    private int MARK_LUCKY = 6; //中奖标记
    private static Handler handler = new Handler();
    private LuckyPanelView luckyPanelView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        luckyPanelView = (LuckyPanelView) findViewById(R.id.lucky_panel_view);

        list.add(new ItemDataBean("iPhone 8", R.mipmap.lucky_prize1));
        list.add(new ItemDataBean("爱奇艺月卡会员", R.mipmap.lucky_prize6));
        list.add(new ItemDataBean("周大福转运珠", R.mipmap.lucky_prize3));
        list.add(new ItemDataBean("暴风魔镜VR眼镜", R.mipmap.lucky_prize5));
        list.add(new ItemDataBean("爱奇艺月卡会员", R.mipmap.lucky_prize6));
        list.add(new ItemDataBean("爱奇艺月卡会员", R.mipmap.lucky_prize6));
        list.add(new ItemDataBean("小米体重秤", R.mipmap.lucky_prize4));
        list.add(new ItemDataBean("Beats 耳机", R.mipmap.lucky_prize2));

        luckyPanelView.setItemData(list);


        luckyPanelView.setOnStartClickListener(new LuckyPanelView.OnStartClickListener() {
            @Override
            public void onStartClick() {
                if (System.currentTimeMillis() - drawTime < 5000) {
                    Toast.makeText(TestActivity.this, "心急吃不了热豆腐，请5秒后再点击哦", Toast.LENGTH_SHORT).show();
                    return;
                }

                //开始抽奖
                if (!luckyPanelView.isGameRunning()) {
                    drawTime = System.currentTimeMillis();
                    luckyPanelView.startGame();
                    getLuck();
                }
            }
        });


    }


    private void getLuck() {

        long delay = 0; //延长时间
        long duration = System.currentTimeMillis() - drawTime;
        if (duration < 5000) {
            delay = 5000 - duration;
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (TestActivity.this.isFinishing()) {
                    return;
                }

                luckyPanelView.tryToStop(getPrizePosition(MARK_LUCKY));
                luckyPanelView.setGameListener(new LuckyPanelView.LuckyMonkeyAnimationListener() {
                    @Override
                    public void onAnimationEnd() {
                        //延长1S弹出抽奖结果
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(TestActivity.this, getPrizeName(MARK_LUCKY), Toast.LENGTH_SHORT).show();
                            }
                        }, 1000);
                    }
                });
            }
        }, delay);
    }



    /**
     * 根据奖品等级计算出奖品位置
     * @param prizeGrade
     * @return
     */
    private int getPrizePosition(int prizeGrade) {
        switch (prizeGrade) {
            case 1:
                return 0;
            case 2:
                return 4;
            case 3:
                return 2;
            case 4:
                return 5;
            case 5:
                return 7;
            case 6: //六等奖有三个位置，随机取一个
                int[] position = {1, 3, 6};
                Random random = new Random();
                return position[random.nextInt(3)];
        }
        return prizeGrade;
    }


    /**
     * 奖品名称
     * @param grade
     * @return
     */
    private String getPrizeName(int grade) {
        switch (grade) {
            case 1:
                return "iPhone 8 手机一部";
            case 2:
                return "Beats 耳机一副";
            case 3:
                return "周大福转运珠一颗";
            case 4:
                return "小米体重称一个";
            case 5:
                return "暴风魔镜VR眼镜一副";
            case 6:
                return "爱奇艺月卡会员";
            default:
                return "";
        }
    }


}
