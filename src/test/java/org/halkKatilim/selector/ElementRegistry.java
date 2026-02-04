package org.halkKatilim.selector;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.halkKatilim.constant.ElementInfo;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ElementRegistry {
    INSTANCE;
    ConcurrentMap<String, Object> elementMapList;

    ElementRegistry() {
        String currentWorkingDir = System.getProperty("user.dir");
        initMap(getFileList(currentWorkingDir + "/src/test/resources/elements"));
    }

    public void initMap(List<File> fileList) {
        elementMapList = new ConcurrentHashMap<>();
        Gson gson = new Gson();
        Type elementType = new TypeToken<List<ElementInfo>>() {}.getType();
        fileList.parallelStream().forEach(file -> {
            try (Reader reader = new FileReader(file)) {
                List<ElementInfo> elements = gson.fromJson(reader, elementType);
                if (elements == null) return;
                for (ElementInfo el : elements) {
                    if (el.getKey() != null) {
                        elementMapList.put(el.getKey(), el);
                    }
                }
            } catch (JsonSyntaxException e) {
                System.err.println("❌ JSON format hatası: " + file.getName());
                throw new RuntimeException(e);
            } catch (IOException e) {
                System.err.println("❌ Dosya okunamadı: " + file.getAbsolutePath());
                throw new RuntimeException(e);
            }
        });
    }

    private List<File> getFileList(String directoryName) {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryName))) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".json"))
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("❌ Klasör okunamadı: " + directoryName);
            throw new RuntimeException(e);
        }
    }

    public ElementInfo findElementInfoByKey(String key) {
        return (ElementInfo) elementMapList.get(key);
    }
}