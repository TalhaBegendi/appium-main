package org.halkKatilim.pages;

import org.halkKatilim.utility.appiumUtil.AppiumUtil;
import java.util.HashMap;
import java.util.Map;

public class Pages {

    private final Map<Class<?>, Object> cache = new HashMap<>();
    private final AppiumUtil util;

    public Pages(AppiumUtil util) {
        this.util = util;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> pageClass) {
        return (T) cache.computeIfAbsent(pageClass, this::createPage);
    }

    private <T> T createPage(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor(AppiumUtil.class)
                    .newInstance(util);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Page oluşturulamadı: " + clazz.getSimpleName(),
                    e
            );
        }
    }
}

