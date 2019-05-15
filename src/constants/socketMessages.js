/**
 * param
 * - MK_APP_VERSION
 * - MK_UMENG_APP_KEY
 * - MK_TAOBAO_APP_KEY
 * - MK_USER_AGENT
 * - MK_WEBVIEW_DEBUG
 * return: null
 */
export const J2N_APP_INIT = 'AppInit';

/**
 * param: null
 * return: null
 */
export const J2N_APP_READY = 'AppReady';

/**
 * param
 * - MK_USER_ID
 * return: null
 */
export const J2N_USER_INFO = 'UserInfo';

/**
 * param
 * - MK_APP_PATH
 * return: null
 */
export const J2N_INSTALL_APP = 'InstallApp';

/**
 * param: null
 * error:
 * - MK_CODE
 */
export const J2N_TAOBAO_AUTH = 'TaobaoAuth';

/**
 * param: null
 * return: null
 */
export const J2N_TAOBAO_LOGOUT = 'TaobaoLogout';

/**
 * check notification permission status, android
 * param: null
 * return:
 * - MK_RESULT
 */
export const J2N_CHECK_NOTIFICATION_PERMISSION = 'CheckNotificationPermission';

/**
 * param:
 * - MK_AD_ID
 * - MK_AD_SOURCE
 * - MK_CALLBACK_ID
 * error:
 * - MK_CODE
 */
export const J2N_WATCH_REWARDED_VIDEO_AD = 'WatchRewardedVideoAd';

/**
 * param:
 * - MK_DEVICE_ID
 * - MK_CHANNEL
 * - MK_TK_ID
 * - MK_ANDROID_ID
 * - MK_BINARY_VERSION
 * return: null
 */
export const N2J_DEVICE_ID = 'DeviceId';

/**
 * param: null
 * return: null
 */
export const N2J_SPLASH_FINISH = 'SplashFinish';
/**
 * param:
 * - MK_VIEW_STATE
 * return: null
 */
export const N2J_VIEW_STATE_CHANGE = 'ViewStateChange';

/**
 * param:
 * - MK_CALLBACK_ID
 * - MK_CALLBACK_TYPE
 * return: null
 */
export const N2J_WATCH_REWARDED_VIDEO_AD_EVENT = 'WatchRewardedVideoAdEvent';

/**
 * param:
 * - MK_MESSAGE
 * return: null
 */
export const N2J_PUSH_MESSAGE_RECEIVE = 'PushMessageReceive';


export const MK_DEVICE_ID = 'device_id';
export const MK_CHANNEL = 'channel';
export const MK_TK_ID = 'tk_id';
export const MK_ANDROID_ID = 'android_id';
export const MK_BINARY_VERSION = 'binary_version';
export const MK_APP_VERSION = 'app_version';
export const MK_UMENG_APP_KEY = 'umeng_app_key';
export const MK_TAOBAO_APP_KEY = 'taobao_app_key';
export const MK_USER_AGENT = 'user_agent';
export const MK_WEBVIEW_DEBUG = 'webview_debug';
export const MK_USER_ID = 'user_id';
export const MK_PUSH_APP_ID = 'push_app_id';
export const MK_PUSH_APP_KEY = 'push_app_key';
export const MK_APP_PATH = 'apk_path';
export const MK_CODE = 'code';
export const MK_VIEW_STATE = 'view_state';
export const MK_RESULT = 'result';
export const MK_AD_ID = 'ad_id';
export const MK_AD_SOURCE = 'ad_source';
export const MK_CALLBACK_ID = 'callback_id';
export const MK_CALLBACK_TYPE = 'callback_type';
export const MK_MESSAGE = 'message';
