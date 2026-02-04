package org.halkKatilim.runner;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.PickleWrapper;
import org.halkKatilim.constant.Config;
import org.halkKatilim.deviceConfig.*;
import org.halkKatilim.utility.helpers.SuiteManager;
import org.testng.annotations.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@CucumberOptions(
        features = "featureTest",
        //name = "Hesaplarım > Yatırım Hesabı Aç  - Amerikan Doları",
        glue = {
                "org.halkKatilim.stepDefs",
                "org.halkKatilim.hooks"
        },

        monochrome = true
)
public final class CucumberTestRunner {

    private static String cucumberPlugins() {
        return Config.ENABLE_REPORTING
                ? "pretty,io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
                : "pretty";
    }

    private static final DeviceResolver RESOLVER = new DeviceResolver(DeviceConfigLoader.load());

    @DataProvider(name = "scenarios", parallel = false)
    public Object[][] scenarios() {
        CucumberTestUtils.initIfNeeded(CucumberTestRunner.class);
        List<Object[]> matrix = new ArrayList<>();
        for (PickleWrapper pickle : CucumberTestUtils.loadPickles()) {
            Path featurePath = Paths.get(pickle.getPickle().getUri());
            int line = pickle.getPickle().getScenarioLine();
            String deviceKey = FeatureHeaderParser.resolveDeviceKey(featurePath, line);
            List<DeviceSpec> devices = RESOLVER.resolve(deviceKey);
            for (DeviceSpec device : devices) {
                matrix.add(new Object[]{pickle, device});
            }
        }
        return matrix.toArray(Object[][]::new);
    }

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        System.setProperty("cucumber.plugin", cucumberPlugins());
        SuiteManager.init();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        try {
            CucumberTestUtils.finish();
        } finally {
            SuiteManager.createReport();
            SuiteManager.cleanupAfterSuite();
        }
    }

    @Test(dataProvider = "scenarios")
    public void runScenario(PickleWrapper pickle, DeviceSpec device) {
        DeviceContext.set(device);
        CucumberTestUtils.run(pickle);
    }
}