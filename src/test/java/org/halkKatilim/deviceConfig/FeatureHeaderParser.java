package org.halkKatilim.deviceConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.IntStream;

public final class FeatureHeaderParser {

    private static final String PREFIX = "@devices=";
    private static final String DEFAULT_KEY = "default";
    private static final ConcurrentMap<Path, List<String>> FILE_CACHE = new ConcurrentHashMap<>();

    private FeatureHeaderParser() {}

    public static String resolveDeviceKey(Path featurePath, int scenarioLine) {
        List<String> lines = readAllCached(featurePath);
        String scenarioLevel = findDeviceDirective(lines, scenarioLine - 2, true);
        if (scenarioLevel != null) {
            return scenarioLevel;
        }
        int featureLine = findFeatureLine(lines);
        String featureLevel = findDeviceDirective(lines, featureLine, false);
        return featureLevel != null ? featureLevel : DEFAULT_KEY;
    }

    private static String findDeviceDirective(
            List<String> lines,
            int fromLine,
            boolean stopOnScenario) {
        for (int i = Math.min(fromLine, lines.size() - 1); i >= 0; i--) {
            String line = lines.get(i).trim();
            if (line.startsWith(PREFIX)) {
                return line.substring(PREFIX.length()).trim();
            }
            if (stopOnScenario &&
                    (line.startsWith("Scenario")
                            || line.startsWith("Scenario Outline"))) {
                return null;
            }
        }
        return null;
    }

    private static int findFeatureLine(List<String> lines) {
        return IntStream.range(0, lines.size())
                .filter(i -> lines.get(i).trim().startsWith("Feature"))
                .findFirst()
                .orElse(0);
    }

    private static List<String> readAllCached(Path path) {
        return FILE_CACHE.computeIfAbsent(path, FeatureHeaderParser::readAll);
    }

    private static List<String> readAll(Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new IllegalStateException("Feature file could not be read: " + path, e);
        }
    }
}