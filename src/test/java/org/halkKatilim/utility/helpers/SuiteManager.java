package org.halkKatilim.utility.helpers;

import org.halkKatilim.deviceConfig.DeviceContext;
import org.halkKatilim.enums.Platform;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.halkKatilim.constant.Config.*;
import static org.halkKatilim.utility.terminal.Terminal.*;

public final class SuiteManager {

    private static final AtomicBoolean initialized = new AtomicBoolean(false);
    private static final AtomicBoolean CLEANUP_DONE = new AtomicBoolean(false);

    private SuiteManager() {
    }

    public static void init() {
        synchronized (SuiteManager.class) {
            if (initialized.compareAndSet(false, true)) {
                if (!PARALLEL || GRID || CLEANUP_DONE.compareAndSet(false, true)) {
                    cleanRuntimeFiles();
                    FrameworkLogger.info("🧹 Runtime initialized and cleaned.");
                } else {
                    FrameworkLogger.info("🧩 Parallel LOCAL run → cleanup skipped for this JVM");
                }
            } else {
                FrameworkLogger.debug("↪️ Runtime already initialized, skipping cleanup.");
            }
            prepareExecutionMode();
        }
    }

    private static void cleanRuntimeFiles() {
        RuntimeFileUtils.deleteIfExists(CLEANUP_COUNTER, "cleanup.counter");
        RuntimeFileUtils.deleteIfExists(SERIAL_MARKER_ALL, SERIAL_MARKER_ALL.getName());
        FrameworkLogger.info("[INIT] Runtime dosyaları temizlendi.");
    }

    private static void prepareExecutionMode() {
        if (Files.notExists(ALL_LOCK.toPath())) {
            RuntimeFileUtils.resetFile(ALL_LOCK.getPath());
            FrameworkLogger.debug("🆕 grid.lock oluşturuldu.");
        }
        if (!PARALLEL) {
            FrameworkLogger.info("🧩 SERIAL mod aktif — Appium her senaryoda yeniden başlatılacak.");
            return;
        }
        try (var channel = FileChannel.open(
                ALL_LOCK.toPath(),
                StandardOpenOption.CREATE,
                StandardOpenOption.READ,
                StandardOpenOption.WRITE);
             var lock = channel.tryLock()) {
            if (lock == null) {
                FrameworkLogger.info("⏭️ Grid/Appium başka bir JVM tarafından çalıştırılıyor.");
                return;
            }
            boolean isEmpty = RuntimeFileUtils.isFileEmpty(ALL_LOCK);
            if (isEmpty) {
                try {
                    RuntimeFileUtils.writeText(ALL_LOCK, "started");
                    FrameworkLogger.info("🚀 Bu JVM Grid/Appium başlatıcısı olarak seçildi.");
                    FrameworkLogger.debug("🧹 Runtime initialized and cleaned.");
                } catch (Exception startupError) {
                    RuntimeFileUtils.deleteIfExists(ALL_LOCK, "grid.lock");
                    FrameworkLogger.error("❌ Grid başlatılamadı, lock temizlendi: "
                            + startupError.getMessage());
                    throw startupError;
                }
            } else {
                FrameworkLogger.info("⏭️ Grid/Appium zaten çalışıyor, tekrar başlatılmayacak.");
            }
        } catch (Exception e) {
            FrameworkLogger.error("❌ Grid lock hatası: " + e.getMessage());
        }
    }

    public static void cleanupAfterSuite() {
        if (!PARALLEL) {
            cleanup();
            FrameworkLogger.info("🛑 Seri mod: Appium cleanup AfterSuite’te tamamlandı.");
            initialized.set(false);
            return;
        }
        String jvmPid = getJvmPid();
        int totalJvm = getTotalJvmCount();
        int currentCount = incrementCleanupCounter(totalJvm, jvmPid);
        if (currentCount == totalJvm) {
            FrameworkLogger.info("🛑 Final cleanup triggered by JVM " + jvmPid);
            cleanup();
            resetRuntimeFilesAfterCleanup();
            PlatformCleanupStrategy.GENERIC
                    .handleCleanup(SuiteManager::resetRuntimeFilesAfterCleanup, initialized);
        } else {
            FrameworkLogger.info(String.format(
                    "⏭️ Cleanup skipped by JVM %s (count=%d/%d)", jvmPid, currentCount, totalJvm));
        }
    }

    private static int incrementCleanupCounter(int totalJvm, String jvmPid) {
        try (FileChannel channel = FileChannel.open(CLEANUP_COUNTER.toPath(),
                StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
             FileLock lock = channel.lock()) {
            int currentCount = RuntimeFileUtils.readInt(channel) + 1;
            RuntimeFileUtils.writeInt(channel, currentCount);
            FrameworkLogger.info(String.format("📊 JVM %s finished (count=%d/%d)", jvmPid, currentCount, totalJvm));
            return currentCount;
        } catch (Exception e) {
            FrameworkLogger.error("Exception during AfterSuite counter update: " + e.getMessage());
            throw new RuntimeException("Cleanup counter update failed", e);
        }
    }

    private static void resetRuntimeFilesAfterCleanup() {
        RuntimeFileUtils.deleteIfExists(CLEANUP_COUNTER, "cleanup.counter");
        RuntimeFileUtils.resetFile(ALL_LOCK.getPath());
        RuntimeFileUtils.deleteIfExists(new File("target/runtime/appium_ports.properties"), "appium_ports.properties");
    }

    private static void cleanup() {
        stopDevice();
        runShellScript("scripts/stop-all.sh");
        FrameworkLogger.info("🎉 Cleanup finished.");
    }

    public static void createReport() {
        if (!ENABLE_REPORTING) {
            FrameworkLogger.info("⏭️ Raporlama devre dışı, rapor oluşturulmayacak.");
            return;
        }
        runShellScript("scripts/create-allure-report.sh");
    }

    private static void stopDevice() {
        if (DeviceContext.isSet() && DeviceContext.get().isRealDevice()) {
            FrameworkLogger.info("📱 (Real Device) skip all stop operations");
            return;
        }

        for (Platform platform : Platform.values()) {
            platform.stopIfUsed();
        }
    }

    private static String getJvmPid() {
        return java.lang.management.ManagementFactory.getRuntimeMXBean()
                .getName().split("@")[0];
    }

    private static int getTotalJvmCount() {
        try (InputStream input = new FileInputStream("env/default/appium.properties")) {
            Properties props = new Properties();
            props.load(input);
            return Integer.parseInt(props.getProperty("GRID_MAX_TOTAL_DEVICES", "1"));
        } catch (Exception e) {
            throw new RuntimeException("Could not load appium.properties", e);
        }
    }
}