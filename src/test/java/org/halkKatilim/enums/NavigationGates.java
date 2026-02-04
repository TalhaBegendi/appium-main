package org.halkKatilim.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;

public final class NavigationGates {

    @Getter
    @RequiredArgsConstructor
    public enum Gate {

        CLOSE_INFO("closeButtonMenuItem"),
        CLOSE_POPUP("closePopupButtonMenu"),
        OK_INFO("afterLoginPopup"),
        OK_POPUP_MESSAGE("okPopupMessage"),
        OK_POPUP("okPopupButtonMenu");

        private final String key;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Context {

        LOGIN(EnumSet.of(Gate.OK_INFO)),

        DEFAULT(EnumSet.of(
                Gate.CLOSE_POPUP,
                Gate.OK_POPUP,
                Gate.OK_POPUP_MESSAGE,
                Gate.CLOSE_INFO));

        private final EnumSet<Gate> gates;
    }
}

