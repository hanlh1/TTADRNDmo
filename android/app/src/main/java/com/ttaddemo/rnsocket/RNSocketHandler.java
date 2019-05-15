package com.ttaddemo.rnsocket;

import android.app.Activity;

import com.facebook.react.bridge.Promise;

import java.util.Map;

public interface RNSocketHandler {
    void handle(Activity activity, Map<String, Object> data, Promise promise);
}
