package co.yangdong.umeng;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableNativeMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.module.annotations.ReactModule;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@ReactModule(name = UmengModule.NAME)
public class UmengModule extends ReactContextBaseJavaModule {
    public static final String NAME = "Umeng";
    private ReactApplicationContext context;

    public UmengModule(ReactApplicationContext reactContext) {
        super(reactContext);
        context = reactContext;
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    @SuppressWarnings("unused")
    @ReactMethod
    public void init(ReadableMap options) {
        String appKey = options.getString("appKey");
        String channel = options.getString("channel");
        String pushSecret = options.getString("pushSecret");
        UMConfigure.init(this.context, appKey, channel, UMConfigure.DEVICE_TYPE_PHONE, pushSecret);
    }

    @SuppressWarnings("unused")
    @ReactMethod
    public void onPageStart(String pageName) {
        MobclickAgent.onPageStart(pageName);
    }

    @SuppressWarnings("unused")
    @ReactMethod
    public void onPageEnd(String pageName) {
        MobclickAgent.onPageEnd(pageName);
    }

    @SuppressWarnings("unused")
    @ReactMethod
    public void onEvent(String eventId) {
        MobclickAgent.onEvent(context, eventId);
    }

    @SuppressWarnings("unused")
    @ReactMethod
    public void onEventWithLabel(String eventId, String eventLabel) {
        MobclickAgent.onEvent(context, eventId, eventLabel);
    }

    @SuppressWarnings("unused")
    @ReactMethod
    public void onEventWithMap(String eventId, ReadableMap map) {
        Map<String, String> rMap = new HashMap<String, String>();
        ReadableMapKeySetIterator iterator = map.keySetIterator();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            if (ReadableType.Array == map.getType(key)) {
                rMap.put(key, map.getArray(key).toString());
            } else if (ReadableType.Boolean == map.getType(key)) {
                rMap.put(key, String.valueOf(map.getBoolean(key)));
            } else if (ReadableType.Number == map.getType(key)) {
                rMap.put(key, String.valueOf(map.getInt(key)));
            } else if (ReadableType.String == map.getType(key)) {
                rMap.put(key, map.getString(key));
            } else if (ReadableType.Map == map.getType(key)) {
                rMap.put(key, map.getMap(key).toString());
            }
        }
        MobclickAgent.onEvent(context, eventId, rMap);
    }

    @SuppressWarnings("unused")
    @ReactMethod
    public void onEventWithMapAndCount(String eventId, ReadableMap map, int value) {
        Map<String, String> rMap = new HashMap();
        ReadableMapKeySetIterator iterator = map.keySetIterator();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            if (ReadableType.Array == map.getType(key)) {
                rMap.put(key, map.getArray(key).toString());
            } else if (ReadableType.Boolean == map.getType(key)) {
                rMap.put(key, String.valueOf(map.getBoolean(key)));
            } else if (ReadableType.Number == map.getType(key)) {
                rMap.put(key, String.valueOf(map.getInt(key)));
            } else if (ReadableType.String == map.getType(key)) {
                rMap.put(key, map.getString(key));
            } else if (ReadableType.Map == map.getType(key)) {
                rMap.put(key, map.getMap(key).toString());
            }
        }
        MobclickAgent.onEventValue(context, eventId, rMap, value);
    }

    @SuppressWarnings("unused")
    @ReactMethod
    public void onEventObject(String eventId, ReadableMap property) {
        Map<String, Object> map = new HashMap();
        ReadableMapKeySetIterator iterator = property.keySetIterator();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            if (ReadableType.Array == property.getType(key)) {
                map.put(key, property.getArray(key).toString());
            } else if (ReadableType.Boolean == property.getType(key)) {
                map.put(key, String.valueOf(property.getBoolean(key)));
            } else if (ReadableType.Number == property.getType(key)) {
                map.put(key, String.valueOf(property.getInt(key)));
            } else if (ReadableType.String == property.getType(key)) {
                map.put(key, property.getString(key));
            } else if (ReadableType.Map == property.getType(key)) {
                map.put(key, property.getMap(key).toString());
            }
        }
        MobclickAgent.onEventObject(context, eventId, map);
    }

    @SuppressWarnings("unused")
    @ReactMethod
    public void registerPreProperties(ReadableMap map) {
        ReadableNativeMap map2 = (ReadableNativeMap) map;
        Map<String, Object> map3 = map2.toHashMap();
        Iterator entries = map3.entrySet().iterator();
        JSONObject json = new JSONObject();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            try {
                json.put(key, value);
            } catch (JSONException ignored) {

            }
        }
        MobclickAgent.registerPreProperties(context, json);
    }

    @SuppressWarnings("unused")
    @ReactMethod
    public void unregisterPreProperty(String propertyName) {
        MobclickAgent.unregisterPreProperty(context, propertyName);
    }

    @SuppressWarnings("unused")
    @ReactMethod
    public void getPreProperties(Callback callback) {
        String result = MobclickAgent.getPreProperties(context).toString();
        callback.invoke(result);
    }

    @SuppressWarnings("unused")
    @ReactMethod
    public void clearPreProperties() {
        MobclickAgent.clearPreProperties(context);

    }

    @SuppressWarnings("unused")
    @ReactMethod
    public void setFirstLaunchEvent(ReadableArray array) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < array.size(); i++) {
            if (ReadableType.Array == array.getType(i)) {
                list.add(array.getArray(i).toString());
            } else if (ReadableType.Boolean == array.getType(i)) {
                list.add(String.valueOf(array.getBoolean(i)));
            } else if (ReadableType.Number == array.getType(i)) {
                list.add(String.valueOf(array.getInt(i)));
            } else if (ReadableType.String == array.getType(i)) {
                list.add(array.getString(i));
            } else if (ReadableType.Map == array.getType(i)) {
                list.add(array.getMap(i).toString());
            }
        }
        MobclickAgent.setFirstLaunchEvent(context, list);
    }

    @SuppressWarnings("unused")
    @ReactMethod
    public void profileSignInWithPUID(String puid) {
        MobclickAgent.onProfileSignIn(puid);
    }

    @SuppressWarnings("unused")
    @ReactMethod
    public void profileSignInWithPUIDWithProvider(String provider, String puid) {
        MobclickAgent.onProfileSignIn(provider, puid);
    }

    @SuppressWarnings("unused")
    @ReactMethod
    public void profileSignOff() {
        MobclickAgent.onProfileSignOff();
    }

    @SuppressWarnings("unused")
    @ReactMethod
    public void onKillProcess() {
        MobclickAgent.onKillProcess(this.context);
    }
}
