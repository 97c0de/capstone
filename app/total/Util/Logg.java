package com.han.total.Util;

import android.util.Log;


public class Logg {

    public static void v(String msg) {
        if (!Global.BUILD_RELEASE) Log.v(tag(), "\uD83D\uDC9C " + msg);
    }

    public static void d(String msg) {
        if (!Global.BUILD_RELEASE) Log.d(tag(), "\uD83D\uDC99 " + msg);
    }

    public static void i(String msg) {
        if (!Global.BUILD_RELEASE) Log.i(tag(), "\uD83D\uDC9A " + msg);
    }

    public static void w(String msg) {
        if (!Global.BUILD_RELEASE) Log.w(tag(), "\uD83D\uDC9B " + msg);
    }

    public static void w(Throwable e) {
        if (!Global.BUILD_RELEASE) {
            Log.w(tag(), "\uD83D\uDC9B " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public static void w(Exception e) {
        if (!Global.BUILD_RELEASE) {
            Log.w(tag(), "\uD83D\uDC9B " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public static void e(String msg) {
        if (!Global.BUILD_RELEASE) Log.e(tag(), "\uD83D\uDC94 " + msg);
    }

    public static void e(Throwable e) {
        if (!Global.BUILD_RELEASE) {
            Log.e(tag(), "\uD83D\uDC94 " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public static void e(Exception e) {
        if (!Global.BUILD_RELEASE) {
            Log.e(tag(), "\uD83D\uDC94 " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public static void v(String User, String msg) {
        if (!Global.BUILD_RELEASE) Log.v(tag(User), "\uD83D\uDC9C " + msg);
    }

    public static void d(String User, String msg) {
        if (!Global.BUILD_RELEASE) Log.d(tag(User), "\uD83D\uDC99 " + msg);
    }

    public static void i(String User, String msg) {
        if (!Global.BUILD_RELEASE) Log.i(tag(User), "\uD83D\uDC9A " + msg);
    }

    public static void w(String User, String msg) {
        if (!Global.BUILD_RELEASE) Log.w(tag(User), "\uD83D\uDC9B " + msg);
    }

    public static void w(String User, Throwable e) {
        if (!Global.BUILD_RELEASE) {
            Log.w(tag(User), "\uD83D\uDC9B " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public static void w(String User, Exception e) {
        if (!Global.BUILD_RELEASE) {
            Log.w(tag(User), "\uD83D\uDC9B " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public static void e(String User, String msg) {
        if (!Global.BUILD_RELEASE) Log.e(tag(User), "\uD83D\uDC94 " + msg);
    }

    public static void e(String User, Throwable e) {
        if (!Global.BUILD_RELEASE) {
            Log.e(tag(User), "\uD83D\uDC94 " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public static void e(String User, Exception e) {
        if (!Global.BUILD_RELEASE) {
            Log.e(tag(User), "\uD83D\uDC94 " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private static String tag() {
        StackTraceElement trace = Thread.currentThread().getStackTrace()[4];
        String fileName = trace.getFileName();
        String classPath = trace.getClassName();
        String className = classPath.substring(classPath.lastIndexOf(".") + 1);
        String methodName = trace.getMethodName();
        int lineNumber = trace.getLineNumber();
        String link = "(" + fileName + ":" + lineNumber + ")";
        String path = "APP# " + className + "." + methodName;
        if (path.length() + link.length() > 80) {
            return path.substring(0, 80 - link.length()) + "..." + link;
        } else {
            return path + link;
        }
    }

    private static String tag(String User) {
        StackTraceElement trace = Thread.currentThread().getStackTrace()[4];
        String fileName = trace.getFileName();
        String classPath = trace.getClassName();
        String className = classPath.substring(classPath.lastIndexOf(".") + 1);
        String methodName = trace.getMethodName();
        int lineNumber = trace.getLineNumber();
        String link = "(" + fileName + ":" + lineNumber + ")";
        String path = "User: " + User + "    APP# " + className + "." + methodName;
        if (path.length() + link.length() > 80) {
            return path.substring(0, 80 - link.length()) + "..." + link;
        } else {
            return path + link;
        }
    }
}