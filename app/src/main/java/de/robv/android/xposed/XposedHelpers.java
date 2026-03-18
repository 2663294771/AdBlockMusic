package de.robv.android.xposed;

import java.lang.reflect.Method;

public class XposedHelpers {
    public static Class<?> findClass(String className, ClassLoader classLoader) throws ClassNotFoundException {
        return Class.forName(className, false, classLoader);
    }

    public static Class<?> findClassIfExists(String className, ClassLoader classLoader) {
        try {
            return findClass(className, classLoader);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static Object getObjectField(Object obj, String fieldName) {
        // 编译期占位，实际由 Xposed 反射实现
        return null;
    }

    public static Method findMethodExact(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static void findAndHookMethod(Class<?> clazz, String methodName, Object... parameterTypesAndCallback) {
        // 占位，实际 hook 由 Xposed 完成
    }

    public static void findAndHookMethod(String className, ClassLoader classLoader, String methodName, Object... parameterTypesAndCallback) {
        // 占位
    }
}
