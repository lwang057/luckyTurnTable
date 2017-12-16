package com.lwang.luckyturntable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lwang.luckyturntable.view.LuckyMonkeyPanelView;

public class SudokuTurnTableActivity extends AppCompatActivity {


    private LuckyMonkeyPanelView luckyPanelView;
    private ImageView mDrawBtn;

    private int[] mPrizImgs = {R.mipmap.win_prize1, R.mipmap.win_prize2, R.mipmap.win_prize3,
            R.mipmap.win_prize4, R.mipmap.win_prize5, R.mipmap.win_prize6};

    private String mActId;
    private String mState; //是否抽过奖（0 未抽过，1 已抽过）
    private long drawTime; //抽奖时间
    private boolean wasLogin = false;
    private boolean isPrizeExhausted = false; //奖品是否被抽完


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_turn_table);

        luckyPanelView = (LuckyMonkeyPanelView) findViewById(R.id.lucky_panel);
        mDrawBtn = (ImageView) findViewById(R.id.id_draw_btn);

        mDrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luckyPanelView.isGameRunning();
                luckyPanelView.startGame();

            }
        });
    }





}
