package org.halkKatilim.utility.helpers;

import java.util.Map;
import java.util.stream.Collectors;
import static org.testng.Assert.fail;

public final class FrameworkLogger {

    private static boolean debugEnabled = false;

    private FrameworkLogger() {}

    public static void enableDebug() {
        debugEnabled = true;
    }

    public static void disableDebug() {
        debugEnabled = false;
    }

    public static void info(String msg) {
        System.out.println("ℹ️ " + msg);
    }

    public static void warn(String msg) {
        System.out.println("⚠️ " + msg);
    }

    public static void error(String msg) {
        System.err.println("❌ " + msg);
    }

    public static void debug(String msg) {
        if (debugEnabled) {
            System.out.println("🐞 " + msg);
        }
    }

    public static void json(String title, Map<String, ?> map) {
        String json = map.entrySet().stream()
                .map(e -> "\"" + e.getKey() + "\": \"" + String.valueOf(e.getValue()) + "\"")
                .collect(Collectors.joining(", ", "{ ", " }"));

        System.out.println("📦 " + title + " " + json);
    }

    public static void log(String icon, String message, Object... args) {
        String formatted = (args.length == 0)
                ? message
                : String.format(message, args);
        System.out.println(icon + " " + formatted);
    }

    public static void log(String message, Object... args) {
        log("ℹ️", message, args);
    }

    public static void trace(String msg) {
        if (debugEnabled) {
            System.out.println("🔍 " + msg);
        }
    }

    public static void trace(java.util.function.Supplier<String> msgSupplier) {
        if (debugEnabled) {
            System.out.println("🔍 " + msgSupplier.get());
        }
    }

    public static void logErrorAndFail(String message) {
        error(message);
        fail(message);
    }

    public static void logErrorAndFail(String message, Throwable throwable) {
        error(message + " | HATA: " + throwable.getMessage());
        throwable.printStackTrace();
        fail(message);
    }
}