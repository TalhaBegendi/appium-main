package org.halkKatilim.deviceConfig;

import org.halkKatilim.enums.retail.RetailCustomer;
import org.halkKatilim.enums.Language;

import java.util.Objects;

public final class DeviceContext {

    private static final ThreadLocal<DeviceSpec> DEVICE = new ThreadLocal<>();
    private static final ThreadLocal<Language> LANGUAGE = new ThreadLocal<>();
    private static final ThreadLocal<RetailCustomer> CUSTOMER = new ThreadLocal<>();

    private DeviceContext() {}

    public static void set(DeviceSpec device) {
        Objects.requireNonNull(device, "DeviceSpec must not be null");
        DEVICE.set(device);
    }

    public static DeviceSpec get() {
        DeviceSpec device = DEVICE.get();
        if (device == null) {
            throw new IllegalStateException("DeviceContext is not initialized for this thread. " + "Did you forget to call DeviceContext.set(...) ?");
        }
        return device;
    }

    public static void setLanguage(Language language) {
        Objects.requireNonNull(language, "Language must not be null");
        LANGUAGE.set(language);
    }

    public static void setCustomer(RetailCustomer retailCustomer) {
        Objects.requireNonNull(retailCustomer, "Customer must not be null");
        CUSTOMER.set(retailCustomer);
    }

    public static boolean isSet() {
        return DEVICE.get() != null;
    }

    public static Language getLanguage() {
        return LANGUAGE.get();
    }

    public static RetailCustomer getCustomer() {
        return CUSTOMER.get();
    }

    public static void clear() {
        DEVICE.remove();
        LANGUAGE.remove();
        CUSTOMER.remove();
    }
}