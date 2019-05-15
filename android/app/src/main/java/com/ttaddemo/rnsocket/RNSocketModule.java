package com.ttaddemo.rnsocket;

import android.os.Bundle;

import com.facebook.react.BuildConfig;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by liangmayong on 2018/2/27.
 */
public class RNSocketModule extends ReactContextBaseJavaModule {
    private static final Object lock = new Object();
    private static volatile RNSocketModule mInstance;
    private static final AtomicBoolean mReady = new AtomicBoolean(false);
    private static final Map<String, RNSocketHandler> mHandlers = Collections.synchronizedMap(new HashMap<>());
    private static List<Event> mPendingEvents = Collections.synchronizedList(new ArrayList<>());

    public RNSocketModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mInstance = this;
    }

    public static void register(String type, RNSocketHandler handler) {
        if (handler == null) {
            mHandlers.remove(type);
        } else {
            mHandlers.put(type, handler);
        }
    }


    public static void post(String type, Map<String, Object> data) {
        Event event = new Event(type, data);
        // cache event if the bridge is not ready
        if (mInstance == null || !mReady.get()) {
            mPendingEvents.add(event);
            return;
        }
        mInstance.handleSendEvent(event);
    }

    @Override
    public String getName() {
        return RNSocketConstants.MODULE_NAME;
    }

    @ReactMethod
    public void send(String type, ReadableMap data, Promise promise) {
        if (type.equals(RNSocketConstants.J2N_APP_INIT)) {
            mReady.set(true);
            synchronized (lock) {
                for (int i = 0; i < mPendingEvents.size(); i++) {
                    handleSendEvent(mPendingEvents.get(i));
                }
                if (!BuildConfig.DEBUG) {
                    // clear pending events on release
                    mPendingEvents.clear();
                }
            }
        }
        RNSocketHandler handler = mHandlers.get(type);
        if (handler != null && getCurrentActivity() != null) {
            handler.handle(getCurrentActivity(), data.toHashMap(), new PromiseProxy(promise));
        } else {
            promise.resolve(null);
        }
    }

    private void handleSendEvent(Event event) {
        // send event by device event emitter
        this.getReactApplicationContext()
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("NativeEvent", event.toWritableMap());
    }

    private static class Event {

        private String type;
        private Map<String, Object> data;

        Event(String type, Map<String, Object> data) {
            this.type = type;
            this.data = data;
        }

        WritableMap toWritableMap() {
            WritableMap body = Arguments.createMap();
            body.putString("type", type);
            body.putMap("data", Arguments.makeNativeMap(data));
            return body;
        }

    }

    private static class PromiseProxy implements Promise {
        private Promise mTarget;

        PromiseProxy(Promise target) {
            mTarget = target;
        }

        // this method is copied from com.facebook.react.bridge.Arguments where it's a private method
        static Object makeNativeObject(Object object) {
            if (object == null) {
                return null;
            } else if (object instanceof Float ||
                    object instanceof Long ||
                    object instanceof Byte ||
                    object instanceof Short) {
                return new Double(((Number) object).doubleValue());
            } else if (object.getClass().isArray()) {
                return Arguments.makeNativeArray(object);
            } else if (object instanceof List) {
                return Arguments.makeNativeArray((List) object);
            } else if (object instanceof Map) {
                return Arguments.makeNativeMap((Map<String, Object>) object);
            } else if (object instanceof Bundle) {
                return Arguments.makeNativeMap((Bundle) object);
            } else {
                // Boolean, Integer, Double, String, WritableNativeArray, WritableNativeMap
                return object;
            }
        }

        @Override
        public void resolve(Object value) {
            // convert to native object
            mTarget.resolve(makeNativeObject(value));
        }

        @Override
        public void reject(String code, String message) {
            mTarget.reject(code, message);
        }

        @Override
        public void reject(String code, Throwable e) {
            mTarget.reject(code, e);
        }

        @Override
        public void reject(String code, String message, Throwable e) { mTarget.reject(code, message, e); }

        @Override
        public void reject(Throwable throwable) { mTarget.reject(throwable); }


        /* ---------------------------
         *  With userInfo WritableMap
         * --------------------------- */

        @Override
        public void reject(Throwable throwable, WritableMap userInfo) { mTarget.reject(throwable, userInfo); }

        @Override
        public void reject(String code, WritableMap userInfo) { mTarget.reject(code, userInfo); }

        @Override
        public void reject(String code, Throwable throwable, WritableMap userInfo) { mTarget.reject(code, throwable, userInfo); }

        @Override
        public void reject(String code, String message, WritableMap userInfo) { mTarget.reject(code, message, userInfo); }

        @Override
        public void reject(String code, String message, Throwable throwable, WritableMap userInfo) {
            mTarget.reject(code, message, throwable, userInfo);
        }

        /* ------------
         *  Deprecated
         * ------------ */
        @Deprecated
        @Override
        public void reject(String message) {
            mTarget.reject(message);
        }

    }
}
