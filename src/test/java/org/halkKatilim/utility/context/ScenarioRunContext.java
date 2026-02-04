package org.halkKatilim.utility.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ScenarioRunContext {


    private final ThreadLocal<ScenarioRunContext> instance = ThreadLocal.withInitial(ScenarioRunContext::new);

    public final Map<String, Object> runContext = new ConcurrentHashMap<>();

    public  ScenarioRunContext getInstance() {
        return instance.get();
    }

    public void setProperty(String key, Object value) {
        if (value != null) {
            runContext.put(key, value);
        } else {
            runContext.remove(key);
        }
    }

    public <T> T getProperty(String key) {
        return (T) runContext.get(key);
    }
}