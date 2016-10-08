package com.jiang.android.push;

import android.content.Context;

import com.huawei.android.pushagent.api.PushManager;
import com.jiang.android.push.emui.EMHuaweiPushReceiver;
import com.jiang.android.push.model.TokenModel;
import com.jiang.android.push.utils.RomUtil;
import com.jiang.android.push.utils.Target;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by jiang on 2016/10/8.
 */

public class Push {

    public static void register(Context context, boolean debug) {
        if (context == null)
            return;
        if (RomUtil.rom() == Target.EMUI) {
            PushManager.requestToken(context);
            Map<String, Long> maps = new HashMap<>();
            maps.put("name", UUID.randomUUID().timestamp());
            PushManager.setTags(context, maps);
            return;

        }
        if (RomUtil.rom() == Target.MIUI) {
            MiPushClient.registerPush(context, Const.getMiui_app_id(), Const.getMiui_app_key());
            return;
        }
        if (RomUtil.rom() == Target.FLYME) {
            com.meizu.cloud.pushsdk.PushManager.register(context, Const.getFlyme_app_id(), Const.getFlyme_app_key());
            return;
        }

        if (RomUtil.rom() == Target.JPUSH) {
            JPushInterface.init(context);
            JPushInterface.setDebugMode(debug);
            return;
        }


    }


    public static TokenModel getToken(Context context) {
        if (context == null)
            return null;
        TokenModel result = new TokenModel();
        result.setTarget(RomUtil.rom());
        if (RomUtil.rom() == Target.EMUI) {
            result.setToken(EMHuaweiPushReceiver.getmToken());
        }
        if (RomUtil.rom() == Target.MIUI) {
            result.setToken(MiPushClient.getRegId(context));
        }
        if (RomUtil.rom() == Target.FLYME) {
            result.setToken(com.meizu.cloud.pushsdk.PushManager.getPushId(context));
        }

        if (RomUtil.rom() == Target.JPUSH) {
            result.setToken(JPushInterface.getRegistrationID(context));
        }
        return result;

    }


    /**
     * 停止推送
     */
    public static void stop(Context context) {
        if (context == null)
            return;
        if (RomUtil.rom() == Target.EMUI) {
            PushManager.enableReceiveNormalMsg(context, false);
            PushManager.enableReceiveNotifyMsg(context, false);
            return;

        }
        if (RomUtil.rom() == Target.MIUI) {
            MiPushClient.pausePush(context, null);
            return;
        }
        if (RomUtil.rom() == Target.FLYME) {
            com.meizu.cloud.pushsdk.PushManager.unRegister(context, Const.getFlyme_app_id(), Const.getFlyme_app_key());
            return;
        }

        if (RomUtil.rom() == Target.JPUSH) {
            if (!JPushInterface.isPushStopped(context))
                JPushInterface.stopPush(context);
            return;
        }

    }


    /**
     * 开始推送
     */
    public static void start(Context context) {
        if (context == null)
            return;
        if (RomUtil.rom() == Target.EMUI) {
            PushManager.enableReceiveNormalMsg(context, true);
            PushManager.enableReceiveNotifyMsg(context, true);
            return;
        }
        if (RomUtil.rom() == Target.MIUI) {
            MiPushClient.resumePush(context, null);
            return;
        }
        if (RomUtil.rom() == Target.FLYME) {
            com.meizu.cloud.pushsdk.PushManager.register(context, Const.getMiui_app_id(), Const.getFlyme_app_key());
            return;
        }
        if (RomUtil.rom() == Target.JPUSH) {
            if (JPushInterface.isPushStopped(context))
                JPushInterface.resumePush(context);
            return;
        }
    }

}