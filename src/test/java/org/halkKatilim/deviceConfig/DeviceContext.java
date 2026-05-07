package org.halkKatilim.deviceConfig;

import org.halkKatilim.enums.UserType;
import org.halkKatilim.enums.Language;
import org.halkKatilim.testData.retail.moneyTransfer.CustomerEntry;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public final class DeviceContext {

    private static final ThreadLocal<DeviceSpec> DEVICE = new ThreadLocal<>();
    private static final ThreadLocal<Language> LANGUAGE = new ThreadLocal<>();
    private static final ThreadLocal<Map<UserType, Object>> CUSTOMER_CONTEXT = ThreadLocal.withInitial(() -> new EnumMap<>(UserType.class));
    private static final ThreadLocal<UserType> CURRENT_USER_TYPE = new ThreadLocal<>();

    public static void setCurrentUserType(UserType type) {
        CURRENT_USER_TYPE.set(type);
    }

    public static UserType getCurrentUserType() {
        return CURRENT_USER_TYPE.get();
    }

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

    public static void setCustomer(CustomerEntry<?>... entries) {
        Map<UserType, Object> context = CUSTOMER_CONTEXT.get();
        for (CustomerEntry<?> entry : entries) {
            Objects.requireNonNull(entry.type(), "UserType must not be null");
            Objects.requireNonNull(entry.customer(), "Customer must not be null");
            context.put(entry.type(), entry.customer());
        }
    }

    public static boolean isSet() {
        return DEVICE.get() != null;
    }

    public static Language getLanguage() {
        return LANGUAGE.get();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getCustomer(UserType type) {
        return (T) CUSTOMER_CONTEXT.get().get(type);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getCurrentCustomer() {
        UserType type = CURRENT_USER_TYPE.get();
        if (type == null) throw new IllegalStateException("Current UserType is not set.");
        return (T) CUSTOMER_CONTEXT.get().get(type);
    }

    public static void clear() {
        DEVICE.remove();
        LANGUAGE.remove();
        CUSTOMER_CONTEXT.remove();
        CURRENT_USER_TYPE.remove();
    }
}