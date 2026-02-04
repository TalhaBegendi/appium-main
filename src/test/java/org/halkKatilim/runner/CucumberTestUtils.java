package org.halkKatilim.runner;

import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import java.util.*;

public final class CucumberTestUtils {

    private static TestNGCucumberRunner runner;
    private static List<PickleWrapper> pickles;

    private CucumberTestUtils() {}

    public static synchronized void initIfNeeded(Class<?> runnerClass) {
        if (runner != null) {
            return;
        }
        runner = new TestNGCucumberRunner(runnerClass);
        Object[][] scenarios = runner.provideScenarios();
        pickles = new ArrayList<>(scenarios.length);
        for (Object[] row : scenarios) {
            PickleWrapper pickle = (PickleWrapper) row[0];
            pickles.add(pickle);
        }
    }

    private static void ensureInit() {
        if (runner == null) {
            throw new IllegalStateException("CucumberTestUtils.initIfNeeded(...) çağrılmadan kullanıldı");
        }
    }

    public static List<PickleWrapper> loadPickles() {
        ensureInit();
        return Collections.unmodifiableList(pickles);
    }

    public static void run(PickleWrapper pickle) {
        ensureInit();
        runner.runScenario(pickle.getPickle());
    }

    public static synchronized void finish() {
        if (runner == null) {
            return;
        }
        try {
            runner.finish();
        } finally {
            runner = null;
            pickles = null;
        }
    }
}