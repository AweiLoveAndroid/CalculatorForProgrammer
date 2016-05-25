package com.example.calculatorforprogrammer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;


public class AdActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        RelativeLayout adsParent = (RelativeLayout) this
                .findViewById(R.id.adsRl);
        SplashAdListener listener=new SplashAdListener() {
            @Override
            public void onAdDismissed() {
                Log.i("RSplashActivity", "onAdDismissed");
                jumpWhenCanClick();// ��ת������Ӧ��������
            }

            @Override
            public void onAdFailed(String arg0) {
                Log.i("RSplashActivity", "onAdFailed");
                jump();
            }

            @Override
            public void onAdPresent() {
                Log.i("RSplashActivity", "onAdPresent");
            }

            @Override
            public void onAdClick() {
                Log.i("RSplashActivity", "onAdClick");
                //���ÿ����ɽ��ܵ��ʱ���ûص�����
            }
        };

        /**
         * ���캯����
         * new SplashAd(Context context, ViewGroup adsParent,
         * 				SplashAdListener listener,String adPlaceId, boolean canClick, SplashType splashType);
         */
        String adPlaceId = "7777777";//��Ҫ�����������Ĺ��λID
        new SplashAd(this, adsParent, listener, adPlaceId, true, SplashAd.SplashType.REAL_TIME);
    }

    /**
     * �����ÿ����ɵ��ʱ����Ҫ�ȴ���תҳ��رպ����л������������ڡ��ʴ�ʱ��Ҫ����waitingOnRestart�жϡ�
     * ���⣬�����������Ҫ��onRestart�е���jumpWhenCanClick�ӿڡ�
     */
    public boolean waitingOnRestart=false;
    private void jumpWhenCanClick() {
        Log.d("test", "this.hasWindowFocus():" + this.hasWindowFocus());
        if(this.hasWindowFocus()||waitingOnRestart){
            this.startActivity(new Intent(AdActivity.this, MainActivity.class));
            this.finish();
        }else{
            waitingOnRestart=true;
        }

    }

    /**
     * ���ɵ���Ŀ�����ʹ�ø�jump��������������jumpWhenCanClick
     */
    private void jump() {
        this.startActivity(new Intent(AdActivity.this, MainActivity.class));
        this.finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(waitingOnRestart){
            jumpWhenCanClick();
        }
    }
}
