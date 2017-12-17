package com.lwang.luckyturntable;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lwang.luckyturntable.view.LuckyMonkeyPanelView;

import java.util.Random;

public class SudokuTurnTableActivity extends AppCompatActivity {


    private LuckyMonkeyPanelView luckyPanelView;
    private ImageView mDrawBtn;
    private long drawTime; //抽奖时间
    private int MARK_LUCKY = 6; //中奖标记
    private static Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_turn_table);

        luckyPanelView = (LuckyMonkeyPanelView) findViewById(R.id.lucky_panel);
        mDrawBtn = (ImageView) findViewById(R.id.id_draw_btn);

        mDrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - drawTime < 5000) {
                    Toast.makeText(SudokuTurnTableActivity.this, "心急吃不了热豆腐，请5秒后再点击哦", Toast.LENGTH_SHORT).show();
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

                if (SudokuTurnTableActivity.this.isFinishing()) {
                    return;
                }

                luckyPanelView.tryToStop(getPrizePosition(MARK_LUCKY));
                luckyPanelView.setGameListener(new LuckyMonkeyPanelView.LuckyMonkeyAnimationListener() {
                    @Override
                    public void onAnimationEnd() {
                        //延长1S弹出抽奖结果
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SudokuTurnTableActivity.this, getPrizeName(MARK_LUCKY), Toast.LENGTH_SHORT).show();
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
