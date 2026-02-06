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

    public void openMainMenu() {
        appiumUtil.clickElement("menuItem");
        MAIN_MENU.runAssertion();
        log.info(LOG_MENU_OPENED);
    }

    public void navigateToMenuOption(String option) {
        openMainMenu();
        appiumUtil.navigate(option, "menuTitleItem", null, MENU);
    }

    public void navigateMenuPathAndVerify(String path, String assertionKey, String assertionModeKey, String textSourceOrder) {
        openMainMenu();
        AppiumUtil.StepResult result = appiumUtil.navigateWithAssertion(path, assertionKey, "menuTitleItem", "lastMenuOption", MENU);
        AssertionKey key = result.key();
        TextSource source = (textSourceOrder == null || textSourceOrder.isBlank())
                ? null
                : TextSource.valueOf(textSourceOrder);
        AssertionMode mode = AssertionMode.valueOf(assertionModeKey);
        executeAssertion(mode, key, result.steps(), source);
        log.info(LOG_MENU_PATH_COMPLETED, String.join(" -> ", result.steps()));
    }

    private void executeAssertion(AssertionMode mode, AssertionKey key, List<String> steps, TextSource source) {
        switch (mode) {
            case PRESENCE -> key.runAssertion();
            case EQUAL -> key.getAssertion().verifyText(steps, key, source);
            case PRESENCE_THEN_EQUAL -> verifyPresenceOrText(key, steps, source);
        }
    }

    private void verifyPresenceOrText(AssertionKey key, List<String> steps, TextSource source) {
        Optional.ofNullable(key.getFallback())
                .filter(fallback ->
                        key.getAssertion()
                                .assertElementExistsSilent(fallback.getElementKey()))
                .ifPresentOrElse(
                        fallback -> {},
                        () -> key.getAssertion().verifyText(steps, key, source)
                );
    }
}