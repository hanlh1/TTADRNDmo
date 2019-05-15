package com.ttaddemo.rnsocket;
import android.app.Activity;

import com.facebook.react.bridge.Promise;
import com.ttaddemo.ad.ADManager;

import java.util.Map;



public class RNSocketConfig {
    public static void config() {
        RNSocketModule.register(RNSocketConstants.J2N_WATCH_REWARDED_VIDEO_AD, new RNSocketHandler() {
            @Override
            public void handle(Activity activity, Map<String, Object> data, Promise promise) {
                ADManager adManager  = new ADManager();
                adManager.loadAD(activity,data,promise);

            }
        });


    }


}
