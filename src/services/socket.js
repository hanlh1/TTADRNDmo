import {
    NativeModules,
    NativeEventEmitter,
    Platform,
    DeviceEventEmitter,
} from 'react-native';

const reactNativeSocket = NativeModules.SHOPReactNativeSocket;


// send
const send = (type, data = {}) => {
    console.warn(type,data);
    console.warn(reactNativeSocket);
    console.warn(reactNativeSocket.send);
    return reactNativeSocket.send(type, data);
}

// event
const handlersByType = new Map();

const createRemover = (type, handler) => {
    const remove = () => {
        const handlers = handlersByType.get(type);
        if (!handlers) {
            return;
        }
        const index = handlers.indexOf(handler);
        if (index === -1) {
            return;
        }
        handlers.splice(index, 1);
    };
    return { remove };
};

const on = (type, handler) => {
    if (!handlersByType.has(type)) {
        handlersByType.set(type, []);
    }
    const remover = createRemover(type, handler);
    const handlers = handlersByType.get(type);
    if (handlers.includes(handler)) {
        return remover;
    }
    handlers.push(handler);
    return remover;
};

const eventEmitter = Platform.OS === 'ios'
    ? /* ios */ new NativeEventEmitter(reactNativeSocket)
    : /* android */ DeviceEventEmitter;
eventEmitter.addListener('NativeEvent', (body) => {
    const { type, data } = body;
    const handlers = handlersByType.get(type);
    if (!handlers) {
        return;
    }
    handlers.forEach(handler => handler(data));
});

// put methods together
const ReactNativeSocket = {
    send,
    on,
};
export default ReactNativeSocket;
