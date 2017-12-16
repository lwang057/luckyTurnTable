package com.lwang.luckyturntable;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hnzycfc.zyxj.api.ApiFactory;
import com.hnzycfc.zyxj.api.ApiUtil;
import com.hnzycfc.zyxj.api.ServerException;
import com.hnzycfc.zyxj.bean.DrawCheckParam;
import com.hnzycfc.zyxj.bean.GetLuckDrawResult;
import com.hnzycfc.zyxj.bean.LuckActivityIdParam;
import com.hnzycfc.zyxj.bean.PrizeBean;
import com.hnzycfc.zyxj.bean.QueryCustomerInfoParam;
import com.hnzycfc.zyxj.bean.QueryCustomerStatusParam;
import com.hnzycfc.zyxj.bean.QueryCustomerStatusResult;
import com.hnzycfc.zyxj.bean.User;
import com.hnzycfc.zyxj.consts.Const;
import com.hnzycfc.zyxj.consts.HawkKey;
import com.hnzycfc.zyxj.utils.UserInfoManager;
import com.hnzycfc.zyxj.utils.Utils;
import com.hnzycfc.zyxj.view.luckymonkeypanel.LuckyMonkeyPanelView;
import com.orhanobut.hawk.Hawk;

import java.net.SocketException;
import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by weichenxu on 2017/11/1.
 */

public class LuckyDrawActivity extends BaseActivity {

    @Bind(R.id.lucky_panel)
    LuckyMonkeyPanelView luckyPanelView;
    @Bind(R.id.id_draw_btn)
    ImageView mDrawBtn;
    @Bind(R.id.id_scrollbar)
    ScrollView mScroll;

    public static final String PARAM_ACTIVITY_ID = "activity_id";

    private int[] mPrizImgs = {R.mipmap.win_prize1, R.mipmap.win_prize2, R.mipmap.win_prize3,
            R.mipmap.win_prize4, R.mipmap.win_prize5, R.mipmap.win_prize6};

    private String mActId;
    private String mState; //是否抽过奖（0 未抽过，1 已抽过）
    private long drawTime; //抽奖时间
    private boolean wasLogin = false;
    private boolean isPrizeExhausted = false; //奖品是否被抽完

    public static void start(Context context, String actId) {
        Intent intent = new Intent(context, LuckyDrawActivity.class);
        intent.putExtra(PARAM_ACTIVITY_ID, actId);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        selectColor();
    }

    @Override
    public void initView() {
        mActId = getIntent().getStringExtra(PARAM_ACTIVITY_ID);
    }

    private void selectColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.color_191919));
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_lucky_draw;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!wasLogin && UserInfoManager.isLogin()) {
            wasLogin = true;
            String status = UserInfoManager.getUser().getStatus();
            if (!TextUtils.isEmpty(status)) {
                if ("7".equals(status)) { //只有已经激活的用户才有可能有抽奖机会
                    luckDrawStateQuery();
                }
            } else {
                queryCustomerStatus();
            }
        }
    }

    @OnClick({R.id.btn_back, R.id.tv_rule_btn, R.id.tv_my_prize, R.id.id_draw_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_rule_btn:
                mScroll.fullScroll(ScrollView.FOCUS_DOWN);
                break;
            case R.id.tv_my_prize:
                if (UserInfoManager.checkUserLoggedIn(this)) {
                    String status = UserInfoManager.getUser().getStatus();
                    if (!TextUtils.isEmpty(status)) {
                        if ("7".equals(status)) { //已激活的用户才有可能有抽奖机会
                            myLuckDrawQuery(); //先查是否有中奖信息
                        } else {
                            showToast(R.string.not_drawed_toast);
                        }
                    } else {
                        queryCustomerStatus();
                    }
                }
                break;
            case R.id.id_draw_btn:
                //判断是否登录
                if (!UserInfoManager.checkUserLoggedIn(this, UserInfoManager.getTempUserName())) {
                    //showToast(R.string.login_to_draw_prize);
                    return;
                }
                //如果已抽过奖
                if ("1".equals(mState)) {
                    showToast(R.string.has_drawed_toast);
                    return;
                }

                if (System.currentTimeMillis() - drawTime < 5000) {
                    showToast(R.string.draw_too_frequency_toast);
                    return;
                }

                String status = UserInfoManager.getUser().getStatus();
                if (!TextUtils.isEmpty(status)) {
                    if ("7".equals(status)) { //已激活的用户才有可能有抽奖机会
                        //开始抽奖
                        if (!luckyPanelView.isGameRunning()) {
                            drawTime = System.currentTimeMillis();
                            luckyPanelView.startGame();
                            getLuckDraw();
                        }
                    } else { //引导用户激活
                        showGuidDialog();
                    }
                } else {
                    queryCustomerStatus();
                }
                break;
            default:
                break;
        }
    }

    //引导用户激活、借款
    private void showGuidDialog() {
        boolean limitActivated; //是否已激活额度
        String status = UserInfoManager.getUser().getStatus();
        if ("6".equals(status)) {
            showToast(R.string.applying_toast);
            return;
        } else if ("7".equals(status)) {
            limitActivated = true;
        } else {
            limitActivated = false;
        }
        new MaterialDialog.Builder(this)
                .content(limitActivated ? R.string.not_extract_money_msg : R.string.not_activated_msg)
                .contentColor(ContextCompat.getColor(this, R.color.textColorDark))
                .contentGravity(GravityEnum.CENTER)
                .canceledOnTouchOutside(false)
                .positiveText(limitActivated ? R.string.go_extract_money : R.string.textGetLimit)
                .positiveColor(ContextCompat.getColor(this, R.color.colorAccent))
                .onPositive((dialog, which) -> {
                    if(limitActivated) { //已激活，提款
                        extractMoney();
                    } else if ("5".equals(status)){ //已拒绝
                        LimitActivateActivity.start(this);
                    } else { //未激活，引导激活
                        GettingLimitSecondActivity.start(this, status);
                    }
                })
                .negativeColor(ContextCompat.getColor(this, R.color.red_f4333c))
                .negativeText(R.string.cancel)
                .show();
    }

    //领奖对话框
    private void showWinPrizeDialog(GetLuckDrawResult prize) {
        boolean isMaterial = isMaterialPrize(prize.getPrizeGrade());
        Dialog dialog = new Dialog(this, R.style.MyDialogStyle);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogLayout = inflater.inflate(R.layout.dialog_win_prize, null);
        ImageView prizePic = (ImageView) dialogLayout.findViewById(R.id.id_prize_pic);
        TextView tvCong = (TextView) dialogLayout.findViewById(R.id.tv_congratulations);
        TextView tvPrizeName = (TextView) dialogLayout.findViewById(R.id.tv_prize_name);
        TextView tvPrizeTips = (TextView) dialogLayout.findViewById(R.id.tv_prize_tips);
        TextView tvGetPrize = (TextView) dialogLayout.findViewById(R.id.tv_get_prize);
        tvGetPrize.setOnClickListener(view -> {
            if (isMaterial) { //实物奖品去领奖
                CompleteReceivingInfoActivity.start(this, prize.getId());
            } else { //虚拟奖品，跳转到奖品详情页去查看
                PrizeBean prizeBean = new PrizeBean();
                prizeBean.setId(prize.getId());
                prizeBean.setPrizeName(prize.getPrizeName());
                prizeBean.setPrizeGrade(prize.getPrizeGrade());
                prizeBean.setPrizeDescrip(prize.getPrizeDescrip());
                prizeBean.setUserCardNo(prize.getUserCardNo());
                PrizeDetailActivity.start(this, prizeBean);
            }
            dialog.dismiss();
        });
        dialogLayout.findViewById(R.id.id_close_btn).setOnClickListener(view -> {
            dialog.dismiss();
        });

        tvCong.setText(isMaterial ? R.string.congratulations_two : R.string.congratulations_one);
        tvPrizeName.setText(getPrizeName(prize.getPrizeGrade()));
        tvPrizeTips.setText(isMaterial ? R.string.lucky_input_your_msg : R.string.view_card_no_tip);
        tvGetPrize.setText(isMaterial ? R.string.get_your_prize : R.string.go_to_see);
        try {
            prizePic.setImageResource(mPrizImgs[prize.getPrizeGrade() - 1]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        dialog.setContentView(dialogLayout);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    //是否是实物奖品
    private boolean isMaterialPrize(int prizeGrade) {
        switch (prizeGrade) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return true;
            default:
                return false;
        }
    }

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

    //查询用户的抽奖状态
    private void luckDrawStateQuery() {
        Utils.showProgressDialog(this);
        ApiFactory.getAppApi().luckDrawStateQuery(new LuckActivityIdParam(mActId))
                .compose(bindToLifecycle())
                .doOnTerminate(Utils::dismissProgressDialog)
                .compose(ApiUtil.genTransformer())
                .subscribe(result -> {
                    Utils.dismissProgressDialog();
                    mState = result.getState();
                }, throwable -> {
                    Utils.dismissProgressDialog();
                    ApiUtil.doOnError(throwable);
                });
    }

    //查询用户的中奖信息
    private void myLuckDrawQuery() {
        Utils.showProgressDialog(this);
        ApiFactory.getAppApi().myLuckDrawQuery(new LuckActivityIdParam(mActId))
                .compose(bindToLifecycle())
                .doOnTerminate(Utils::dismissProgressDialog)
                .compose(ApiUtil.genTransformer())
                .subscribe(result -> {
                    Utils.dismissProgressDialog();
                    if (result == null || result.getPrizeList() == null || result.getPrizeList().size() <= 0) {
                        showToast(R.string.not_drawed_toast);
                    } else if (result.getPrizeList().get(0).getState() == 2) {
                        showToast(R.string.give_up_prize_toast);
                    } else {
                        PrizeDetailActivity.start(this, result.getPrizeList().get(0));
                    }
                }, throwable -> {
                    Utils.dismissProgressDialog();
                    ApiUtil.doOnError(throwable);
                });
    }

    //根据奖品等级计算出奖品位置
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

    //用户抽奖接口
    private void getLuckDraw() {
        ApiFactory.getAppApi().getLuckDraw(new LuckActivityIdParam(mActId))
                .compose(bindToLifecycle())
                .compose(ApiUtil.genTransformer())
                .subscribe(result -> {
                    if (result == null || result.getId() == null) {
                        //奖品已抽完
                        luckyPanelView.reset();
                        showToast(R.string.prize_exhausted);
                        return;
                    }
                    mState = "1";
                    stop(result);
                }, throwable -> {
                    String msg = null;
                    luckyPanelView.reset();
                    if (throwable instanceof SocketException || throwable instanceof HttpException) {
                        msg = getString(R.string.net_error);
                    } else if (throwable instanceof ServerException) {
                        String errorCode = ((ServerException) throwable).getReturnCode();
                        if ("LUCKDRAW008".equals(errorCode)) {
                            //此用户没有抽奖资格（活动期间没有提款），需引导用户提款
                            showGuidDialog();
                            return;
                        }
                        msg = ((ServerException) throwable).getReturnMsg();
                    }
                    showToast(msg);
                });
    }

    static Handler handler = new Handler();
    private void stop(GetLuckDrawResult result) {
        long delay = 0; //延长时间
        long duration = System.currentTimeMillis() - drawTime;
        if (duration < 5000) {
            delay = 5000 - duration;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (LuckyDrawActivity.this.isFinishing()) {
                    return;
                }
                luckyPanelView.tryToStop(getPrizePosition(result.getPrizeGrade()));
                luckyPanelView.setGameListener(new LuckyMonkeyPanelView.LuckyMonkeyAnimationListener() {
                    @Override
                    public void onAnimationEnd() {
                        //延长1S弹出中奖对话框
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showWinPrizeDialog(result);
                            }
                        }, 1000);
                    }
                });
            }
        }, delay);
    }

    //客户实名额度状态信息查询
    private void queryCustomerStatus() {
        Utils.showProgressDialog(this);
        ApiFactory.getAppApi().queryCustomerStatus(new QueryCustomerStatusParam("Y"))
                .compose(bindToLifecycle())
                .doOnTerminate(Utils::dismissProgressDialog)
                .compose(ApiUtil.genTransformer())
                .subscribe(s -> {
                    if (s != null) {
                        User user = UserInfoManager.getUser(UserInfoManager.getTempUserName());
                        if (user != null) {
                            user.setStatus(s.getStatus());
                        }
                        UserInfoManager.setRefreshTime(System.currentTimeMillis());
                        UserInfoManager.setIsNeedRefresh(false);
                        UserInfoManager.setStatusResult(s);
                        UserInfoManager.setUser(user);
                        Hawk.put(HawkKey.CUSTOMERSTATUS, s);
                        Hawk.put(HawkKey.THISLOANTYPE, s.getLoan_typ());

                        //查询抽奖信息
                        if ("7".equals(s.getStatus())) {
                            luckDrawStateQuery();
                        }
                    }
                    Utils.dismissProgressDialog();
                }, throwable -> {
                    Utils.dismissProgressDialog();
                    ApiUtil.doOnError(throwable);
                });
    }

    private void extractMoney() {
        QueryCustomerStatusResult statusResult = UserInfoManager.getStatusResult();
        String defaultLoanType = Hawk.get(HawkKey.DEFAULTLOANTYPE);
        String loanType = null;
        if (statusResult != null) {
            loanType = statusResult.getLoan_typ();
        }
        //检查产品编号是否一致，一致去查询是否补全信息，不一致就是校园贷
        if (!TextUtils.isEmpty(loanType) && !TextUtils.isEmpty(defaultLoanType) && defaultLoanType.equals(loanType)) {
            queryCustomerInfo();
        } else {
            User user = UserInfoManager.getUser(UserInfoManager.getTempUserName());
            drawCheck(user);
        }
    }

    /**
     * 查询是否补全用户信息
     */
    private void queryCustomerInfo() {
        Utils.showProgressDialog(this);
        User user = UserInfoManager.getUser(UserInfoManager.getTempUserName());
        if (user == null) {
            return;
        }
        ApiFactory.getAppApi().queryCustomerInfo(new QueryCustomerInfoParam(user.getIdName(),
                Const.idType, user.getIdNo(), "04"))
                .compose(bindToLifecycle())
                .compose(ApiUtil.genTransformer())
                .subscribe(result -> {
                    if ("Y".equals(result.getInfo_complete_if())) {
                        user.setInfoComplete(true);
                        UserInfoManager.setUser(user);
                        drawCheck(user);
                    } else {
                        startActivity(UserInfoCompleteActivity.class);
                        Utils.dismissProgressDialog();
                    }
                }, throwable -> {
                    Utils.dismissProgressDialog();
                    ApiUtil.doOnError(throwable);
                });
    }

    //贷款申请准入检查
    private void drawCheck(User user) {
        QueryCustomerStatusResult statusResult = UserInfoManager.getStatusResult();
        if (statusResult != null && !TextUtils.isEmpty(statusResult.getAppl_cde())) {
            ApiFactory.getAppApi().drawableCheck(new DrawCheckParam(user.getIdName(), Const.idType, user.getIdNo(), "03", "04", statusResult.getAppl_cde()))
                    .compose(bindToLifecycle())
                    .compose(ApiUtil.genTransformer())
                    .subscribe(result -> {
                        if ("Y".equals(result.getAllow_dn())) {
                            ExtractMoneyFixActivity.start(this, result, "请选择");
                        } else {
                            showToast(result.getCheckMsg());
                        }
                        Utils.dismissProgressDialog();
                    }, throwable -> {
                        Utils.dismissProgressDialog();
                        ApiUtil.doOnError(throwable);
                    });
        } else {
            queryCustomerStatus();
        }
    }
}
