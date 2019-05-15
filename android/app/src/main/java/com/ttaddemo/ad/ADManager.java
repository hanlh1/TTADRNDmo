package com.ttaddemo.ad;

import android.app.Activity;
import android.util.Log;

import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.facebook.react.bridge.Promise;
import com.ttaddemo.rnsocket.RNSocketConstants;
import com.ttaddemo.rnsocket.RNSocketModule;

import java.util.HashMap;
import java.util.Map;

public class ADManager {

    public void loadAD(Activity activity, Map<String, Object> data, Promise promise){
        String rewardName = null;
        int rewardAmount = -1;
        String adId = "";
        String adSource = "";
        String callBackId = null;
        if (data.containsKey(RNSocketConstants.MK_AD_ID)) {
            adId = (String)data.get(RNSocketConstants.MK_AD_ID);
        }

        if(data.containsKey(RNSocketConstants.MK_AD_SOURCE)){
            adSource = (String)data.get(RNSocketConstants.MK_AD_SOURCE);
        }

        if(data.containsKey(RNSocketConstants.MK_CALLBACK_ID)){
            callBackId=(String)data.get(RNSocketConstants.MK_CALLBACK_ID);
        }
        String userid = "123456";
        Log.e(RewardVideoUtils.LOG_TAG,"J2N_WATCH_REWARDED_VIDEO_AD userid:"+userid+"adId："+adId +"adSource："+adSource+" callBackId："+callBackId);
        RewardVideoUtils adUtils = new RewardVideoUtils(activity);
        adUtils.initADSDK();
        Map payload = new HashMap();
        adUtils.loadAd(userid, adId,rewardName,rewardAmount,adSource,callBackId, TTAdConstant.VERTICAL, new ADCallBack() {
            @Override
            public void onAdShow(String callbackId) {
                Log.e(RewardVideoUtils.LOG_TAG,"onAdShow() callBackId:"+callbackId);
            }

            @Override
            public void onVideoComplete(String callbackId) {
                Log.e(RewardVideoUtils.LOG_TAG,"onVideoComplete() callBackId:"+callbackId);

            }

            @Override
            public void onAdClose(String callbackId) {
                payload.put(RNSocketConstants.MK_RESULT, "onAdClose");
                promise.resolve(null);
            }

            @Override
            public void onVideoError(String callbackId) {

            }

            @Override
            public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName,String callbackId) {

            }

            @Override
            public void onRewardVideoCached(String callbackId) {
                Map state = new HashMap();
                state.put(RNSocketConstants.MK_CALLBACK_ID,callbackId);
                state.put(RNSocketConstants.MK_CALLBACK_TYPE, RNSocketConstants.MK_CALLBACK_TYPE_ONLOAD);
                RNSocketModule.post(RNSocketConstants.N2J_WATCH_REWARDED_VIDEO_AD_EVENT, state);
            }

            @Override
            public void onError(int code,String message,String callbackId) {
                String shmCodeError =  ADCodeErrorUtils.getInstance().getSHMCodeError(Integer.toString(code));
                promise.reject(shmCodeError, Integer.toString(code)+"_"+message);
            }
        });;
    }

}
