package org.halkKatilim.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.By;

@Getter
@AllArgsConstructor
public class SelectorInfo {
    private By by;
    private int index;
}
