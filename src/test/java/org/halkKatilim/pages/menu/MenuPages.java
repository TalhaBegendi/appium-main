package org.halkKatilim.pages.menu;

import lombok.extern.slf4j.Slf4j;
import org.halkKatilim.enums.TextSource;
import org.halkKatilim.pages.BasePages;
import org.halkKatilim.utility.appiumUtil.AppiumUtil;
import org.halkKatilim.utility.assertionUtil.enums.AssertionKey;
import org.halkKatilim.utility.assertionUtil.enums.AssertionMode;

import java.util.List;
import java.util.Optional;

import static org.halkKatilim.pages.menu.MenuText.*;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionKey.MAIN_MENU;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionPrefix.MENU;

@Slf4j
public class MenuPages extends BasePages {

    public MenuPages() {
    }

    public void goToMenu() {
        appiumUtil.clickElement("menuItem");
        MAIN_MENU.runAssertion();
        log.info(LOG_MENU_OPENED);
    }

    public void goToOptionFromMenu(String option) {
        goToMenu();
        appiumUtil.navigate(option, "menuTitleItem", null, MENU);
    }

    public void goToFromMenuUnified(String path, String assertion, String assertionModeKey, String order) {
        goToMenu();
        AppiumUtil.StepResult res = appiumUtil.navigateWithAssertion(
                path, assertion, "menuTitleItem", "lastMenuOption", MENU);

        AssertionKey key = res.key();

        TextSource source = (order == null || order.isBlank())
                ? null
                : TextSource.valueOf(order);

        AssertionMode mode = AssertionMode.valueOf(assertionModeKey);
        runAssertion(mode, key, res.steps(), source);

        log.info(LOG_MENU_PATH_COMPLETED, String.join(" -> ", res.steps()));
    }

    private void runAssertion(AssertionMode mode, AssertionKey key, List<String> steps, TextSource source) {
        switch (mode) {
            case PRESENCE -> key.runAssertion();
            case EQUAL -> key.getAssertion().verifyText(steps, key, source);
            case PRESENCE_THEN_EQUAL -> runPresenceThenEqual(key, steps, source);
        }
    }

    private void runPresenceThenEqual(AssertionKey key, List<String> steps, TextSource source) {

        Optional.ofNullable(key.getFallback())
                .filter(fallback -> key.getAssertion().assertElementExistsSilent(fallback.getElementKey()))
                .ifPresentOrElse(
                        fallback -> {
                        },
                        () -> key.getAssertion().verifyText(steps, key, source)
                );
    }
}
