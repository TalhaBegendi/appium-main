package org.halkKatilim.utility.helpers;

import java.util.concurrent.atomic.AtomicBoolean;

public enum PlatformCleanupStrategy {

    GENERIC;

    public void handleCleanup(Runnable resetFiles, AtomicBoolean initialized) {
        resetFiles.run();
        initialized.set(false);
    }
}