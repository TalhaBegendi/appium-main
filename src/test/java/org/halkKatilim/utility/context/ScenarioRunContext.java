package org.halkKatilim.utility.context;

import java.util.HashMap;
import java.util.Map;

public final class ScenarioRunContext {

    private static final ThreadLocal<Map<String, Object>> STORE = ThreadLocal.withInitial(HashMap::new);

    private ScenarioRunContext() {}

    public static void set(String key, Object value) {
        if (value != null) {
            STORE.get().put(key, value);
        } else {
            STORE.get().remove(key);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) STORE.get().get(key);
    }

    public static void clear() {
        STORE.remove();
    }
}