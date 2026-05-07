package org.halkKatilim.pages.menu;

import lombok.extern.slf4j.Slf4j;
import static org.halkKatilim.pages.menu.MenuText.LOG_MENU_OPENED;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionKey.MAIN_MENU;
import lombok.RequiredArgsConstructor;
import org.halkKatilim.utility.appiumUtil.AppiumUtil;

@Slf4j
@RequiredArgsConstructor
public class MenuPages  {

    private final AppiumUtil appiumUtil;

    public void openMainMenu() {
        appiumUtil.clickElement("menuItem");
        MAIN_MENU.runAssertion();
        log.info(LOG_MENU_OPENED);
    }

    public void navigateToMenuOption(String option) {
        openMainMenu();
        appiumUtil.navigate(option, "menuTitleItem");
    }
}