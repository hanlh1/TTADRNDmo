package com.ttaddemo.ad;

public interface ADCallBack {
    void onAdShow(String callbackId);
    void onVideoComplete(String callbackId);
    void onAdClose(String callbackId);
    void onVideoError(String callbackId);
    void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName, String callbackId);
    void onRewardVideoCached(String callbackId);
    void onError(int code, String message, String callbackId);
}
