package org.halkKatilim.utility.context;

import org.halkKatilim.pages.Pages;
import org.halkKatilim.utility.appiumUtil.AppiumUtil;

public final class PageContext {

    private static final ThreadLocal<Pages> PAGES = new ThreadLocal<>();

    public static void init(AppiumUtil util) {
        PAGES.set(new Pages(util));
    }

    public static Pages get() {
        return PAGES.get();
    }

    public static void clear() {
        PAGES.remove();
    }
}



