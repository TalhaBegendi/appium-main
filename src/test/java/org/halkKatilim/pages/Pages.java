package org.halkKatilim.pages;

import lombok.Getter;
import org.halkKatilim.pages.accountsPage.AccountsPages;
import org.halkKatilim.pages.homePage.HomePages;
import org.halkKatilim.pages.menu.MenuPages;
import org.halkKatilim.pages.loginPage.LoginPages;
import org.halkKatilim.pages.moneyTransfer.toAnotherAccount.ToAnotherAccountAccountPages;
import org.halkKatilim.pages.moneyTransfer.toAnotherAccount.ToAnotherAccountIBANPages;

@Getter
public class Pages {
    private final LoginPages loginPage;
    private final HomePages homePage;
    private final MenuPages menuPage;
    private final ToAnotherAccountIBANPages toAnotherAccountIbanPage;
    private final ToAnotherAccountAccountPages toAnotherAccountAccountPage;
    private final AccountsPages accountsPages;

    public Pages() {
        this.loginPage = new LoginPages();
        this.homePage = new HomePages();
        this.menuPage = new MenuPages();
        this.toAnotherAccountIbanPage = new ToAnotherAccountIBANPages();
        this.toAnotherAccountAccountPage = new ToAnotherAccountAccountPages();
        this.accountsPages = new AccountsPages();
    }
}
