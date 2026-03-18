package com.example.qqmusicadblock;

import android.content.Context;
import de.robv.android.xposed.*;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.*;

public class HookMain implements IXposedHookLoadPackage {

    private static final String PKG_QQ_MUSIC = "com.tencent.qqmusic";

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals(PKG_QQ_MUSIC)) return;

        hookSplashAd(lpparam);
        hookPlayerBannerAd(lpparam);
        hookMePageAds(lpparam);
        hookPopupAndVipPromo(lpparam);
    }

    private void hookSplashAd(LoadPackageParam lpparam) {
        try {
            Class<?> splashCls = findClass("com.tencent.qqmusic.ad.splash.SplashAdActivity", lpparam.classLoader);
            findAndHookMethod(splashCls, "onResume", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    ((android.app.Activity) param.thisObject).finish();
                    XposedBridge.log("[QQMusicAdBlock] 跳过开屏广告");
                }
            });
        } catch (Throwable t) {
            XposedBridge.log("[QQMusicAdBlock] Splash Hook 失败: " + t.getMessage());
        }
    }

    private void hookPlayerBannerAd(LoadPackageParam lpparam) {
        try {
            Class<?> adViewCls = findClass("com.qq.e.ads.nativ.NativeExpressAdView", lpparam.classLoader);
            findAndHookMethod(adViewCls, "setVisibility", int.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    param.args[0] = 8;
                }
            });
            Class<?> qqAdContainer = findClassIfExists("com.tencent.qqmusic.ad.view.AdBannerContainer", lpparam.classLoader);
            if (qqAdContainer != null) {
                findAndHookMethod(qqAdContainer, "setVisibility", int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        param.args[0] = 8;
                    }
                });
            }
        } catch (Throwable t) {
            XposedBridge.log("[QQMusicAdBlock] Banner Hook 失败: " + t.getMessage());
        }
    }

    private void hookMePageAds(LoadPackageParam lpparam) {
        try {
            Class<?> adViewHolder = findClass("com.tencent.qqmusic.main.my.viewholder.FeedAdViewHolder", lpparam.classLoader);
            findAndHookMethod(adViewHolder, "onBindViewHolder", Object.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    try {
                        Object itemView = getObjectField(param.thisObject, "itemView");
                        if (itemView instanceof android.view.View) {
                            ((android.view.View) itemView).setVisibility(android.view.View.GONE);
                        }
                    } catch (Exception e) {
                        XposedBridge.log(e.toString());
                    }
                }
            });
        } catch (Throwable t) {
            XposedBridge.log("[QQMusicAdBlock] MePage Hook 失败: " + t.getMessage());
        }
    }

    private void hookPopupAndVipPromo(LoadPackageParam lpparam) {
        try {
            Class<?> vipPopupCls = findClassIfExists("com.tencent.qqmusic.vip.widget.VipPopupDialog", lpparam.classLoader);
            if (vipPopupCls != null) {
                findAndHookMethod(vipPopupCls, "show", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        param.setResult(null);
                        XposedBridge.log("[QQMusicAdBlock] 拦截 VIP 弹窗");
                    }
                });
            }
            findAndHookMethod("android.app.Dialog", lpparam.classLoader, "show", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    param.setResult(null);
                    XposedBridge.log("[QQMusicAdBlock] 拦截通用 Dialog");
                }
            });
        } catch (Throwable t) {
            XposedBridge.log("[QQMusicAdBlock] Popup Hook 失败: " + t.getMessage());
        }
    }
}
