package com.ttaddemo.ad;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;

public class RewardVideoUtils {

    private Activity context;

    private TTAdNative mTTAdNative;
    private TTRewardVideoAd mttRewardVideoAd;

    private boolean mHasShowDownloadActive = false;

    public static String LOG_TAG = "shmAD";

    public RewardVideoUtils(Activity context){
        this.context  = context;

    }
    public void initADSDK(){
        //step1:初始化sdk
        TTAdManager ttAdManager = TTAdManagerHolder.get();
        //step2:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdManagerHolder.get().requestPermissionIfNecessary(context);
        //step3:创建TTAdNative对象,用于调用广告请求接口
        mTTAdNative = ttAdManager.createAdNative(context.getApplicationContext());
    }

    public void showAd(String callbackId){
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e(LOG_TAG,"showAd() callbackId:"+callbackId);
                if (mttRewardVideoAd != null) {
                    //step6:在获取到广告后展示
                    mttRewardVideoAd.showRewardVideoAd(context);
                    mttRewardVideoAd = null;
                }
            }
        });

    }

    /**
     *
     * @param userId
     * @param codeId
     * @param rewardName
     * @param rewardAmount
     * @param adSource
     * @param orientation
     * @param callBack
     */

    public void loadAd(String userId,String codeId,String rewardName,int rewardAmount, String adSource,String callbackId,int orientation,ADCallBack callBack) {
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot.Builder builder = new AdSlot.Builder()
                .setCodeId("916223868")
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920)
                .setUserID(userId)//用户id,必传参数
                .setMediaExtra(adSource) //附加参数，可选
                .setOrientation(orientation); //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL;
        if(!TextUtils.isEmpty(rewardName)) {
            builder.setRewardName(rewardName);
        }

        if(rewardAmount>0){
            builder.setRewardAmount(rewardAmount);
        }
        AdSlot adSlot  = builder.build();

        //step5:请求广告
        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.e(LOG_TAG,"onError() callbackId:"+callbackId);
                if(callBack!=null){
                    callBack.onError(code,message,callbackId);
                }
            }

            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
            @Override
            public void onRewardVideoCached() {
                Log.e(LOG_TAG,"onRewardVideoCached() callbackId:"+callbackId);
                if(callBack!=null){
                    callBack.onRewardVideoCached(callbackId);
                }
                showAd(callbackId);
            }

            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
                Log.e(LOG_TAG,"onRewardVideoAdLoad() callbackId:"+callbackId);
                mttRewardVideoAd = ad;
//              mttRewardVideoAd.setShowDownLoadBar(false);
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        Log.e(LOG_TAG,"onAdShow() callbackId:"+callbackId);
                        if(callBack!=null){
                            callBack.onAdShow(callbackId);
                        }
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        Log.e(LOG_TAG,"onAdVideoBarClick()");
                    }

                    @Override
                    public void onAdClose() {
                        Log.e(LOG_TAG,"onAdClose()");
                        if(callBack!=null){
                            callBack.onAdClose(callbackId);
                        }
                    }

                    //视频播放完成回调
                    @Override
                    public void onVideoComplete() {
                        Log.e(LOG_TAG,"onVideoComplete()");
                        if(callBack!=null){
                            callBack.onVideoComplete(callbackId);
                        }
                    }

                    @Override
                    public void onVideoError() {
                        Log.e(LOG_TAG,"onVideoError()");
                        if(callBack!=null){
                            callBack.onVideoError(callbackId);
                        }
                    }

                    //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                        Log.e(LOG_TAG,"verify:" + rewardVerify + " amount:" + rewardAmount + " name:" + rewardName);
                        if(callBack!=null){
                            callBack.onRewardVerify(rewardVerify,rewardAmount,rewardName,callbackId);
                        }
                    }
                });
                mttRewardVideoAd.setDownloadListener(new TTAppDownloadListener() {
                    @Override
                    public void onIdle() {
                        mHasShowDownloadActive = false;
                    }

                    @Override
                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                        if (!mHasShowDownloadActive) {
                            mHasShowDownloadActive = true;
                            //下载中，点击下载区域暂停
                        }
                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                       //下载暂停，点击下载区域继续
                    }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                        //下载失败，点击下载区域重新下载
                    }

                    @Override
                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                        //下载完成，点击下载区域重新下载
                    }

                    @Override
                    public void onInstalled(String fileName, String appName) {
                        //安装完成，点击下载区域打开
                    }
                });
            }
        });
    }
}
