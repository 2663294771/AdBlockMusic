package de.robv.android.xposed;

public class XposedBridge {
    public static void log(String text) {
        // 编译期占位，运行时由 Xposed 替换
    }
    public static void log(Throwable t) {
        // 占位
    }
}
