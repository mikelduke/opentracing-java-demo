package com.mikelduke.vhs.catalog;

public final class EnvUtil {

    public static String getEnv(String key) {
        return getEnv(key, null);
    }

    public static String getEnv(String key, String defaultValue) {
        String val = defaultValue;

        if (System.getenv(key) != null) {
            val = System.getenv(key);
        }

        val = System.getProperty(key, val);

        return val;
    }
}