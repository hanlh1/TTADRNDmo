import uuidv4 from 'uuid/v4';
import {
    J2N_WATCH_REWARDED_VIDEO_AD,
    N2J_WATCH_REWARDED_VIDEO_AD_EVENT,
    MK_AD_ID,
    MK_AD_SOURCE,
    MK_CALLBACK_ID,
    MK_CALLBACK_TYPE,
} from '../constants/socketMessages';
import ReactNativeSocket from './socket';
import {
    Alert,
} from 'react-native';

const callbacksByCallbackId = new Map();

/* eslint-disable-next-line import/prefer-default-export */
export const watchRewardedVideoAd = (adId = '123456', adSource = 'asdfg', options) => {
    const { onLoad } = options || {
       
    };
    adId = 'han 1';
    adSource = 'han 2';
    console.warn(adSource);
    console.warn(adId);
    Alert.alert(
        'Alert Title',
        'My Alert Msg',
        [
          {text: 'Ask me later', onPress: () => console.log('Ask me later pressed')},
          {text: 'Cancel', onPress: () => console.log('Cancel Pressed'), style: 'cancel'},
          {text: 'OK', onPress: () => console.log('OK Pressed')},
        ],
        { cancelable: false }
      )

    const callbackId = uuidv4();
    callbacksByCallbackId.set(callbackId, { onLoad });
    return ReactNativeSocket.send(J2N_WATCH_REWARDED_VIDEO_AD, {
        [MK_AD_ID]: adId,
        [MK_AD_SOURCE]: adSource,
        [MK_CALLBACK_ID]: callbackId,
    }).then((result) => {
        callbacksByCallbackId.delete(callbackId);
        console.warn('then lhhan');
        Alert.alert(
            'Alert Title then ',
            'My Alert Msg',
            [
              {text: 'Ask me later', onPress: () => console.log('Ask me later pressed')},
              {text: 'Cancel', onPress: () => console.log('Cancel Pressed'), style: 'cancel'},
              {text: 'OK', onPress: () => console.log('OK Pressed')},
            ],
            { cancelable: false }
          )
        return result;
    }).catch((err) => {
        callbacksByCallbackId.delete(callbackId);
        console.warn('err');
        Alert.alert(
            'Alert Title err ',
            'My Alert Msg',
            [
              {text: 'Ask me later', onPress: () => console.log('Ask me later pressed')},
              {text: 'Cancel', onPress: () => console.log('Cancel Pressed'), style: 'cancel'},
              {text: 'OK', onPress: () => console.log('OK Pressed')},
            ],
            { cancelable: false }
          )
        throw err;
    });
};

ReactNativeSocket.on(N2J_WATCH_REWARDED_VIDEO_AD_EVENT, ({
    [MK_CALLBACK_ID]: callbackId,
    [MK_CALLBACK_TYPE]: type,
}) => {
    const callbacks = callbacksByCallbackId.get(callbackId);
    if (!callbacks) {
        return;
    }
    const callback = callbacks[type];
    if (!callback) {
        return;
    }
    callback();
});
