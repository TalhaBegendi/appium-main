package org.halkKatilim.pages.loginPage;

import org.halkKatilim.interfaces.CustomerCapable;

record LoginContext(CustomerCapable customer, Runnable credentialFiller) {}