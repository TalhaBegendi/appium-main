package org.halkKatilim.utility.helpers;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;

import static java.nio.file.StandardOpenOption.*;

public final class RuntimeFileUtils {

    private RuntimeFileUtils() {}

    @FunctionalInterface
    private interface IOAction<T> {
        T run() throws IOException;
    }

    private static <T> T ioWrap(IOAction<T> action, String msg) {
        try {
            return action.run();
        } catch (IOException e) {
            throw new UncheckedIOException(msg, e);
        }
    }

    public static void deleteIfExists(File file, String name) {
        ioWrap(() -> {
            Path path = file.toPath();
            if (Files.exists(path) && Files.deleteIfExists(path)) {
                FrameworkLogger.debug("🧹 %s deleted.".formatted(name));
            }
            return null;
        }, "%s could not be deleted".formatted(name));
    }

    public static File resetFile(String path) {
        return ioWrap(() -> {
            Path filePath = Paths.get(path).toAbsolutePath();
            Files.createDirectories(filePath.getParent());
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                FrameworkLogger.debug("🆕 %s created.".formatted(filePath.getFileName()));
                return filePath.toFile();
            }
            long size = Files.size(filePath);
            if (size > 0) {
                FrameworkLogger.debug("↪️ %s already populated (%d bytes), skipping reset."
                        .formatted(filePath.getFileName(), size));
                return filePath.toFile();
            }
            try (var channel = FileChannel.open(filePath, WRITE, TRUNCATE_EXISTING)) {
                channel.force(true);
            }
            FrameworkLogger.debug("🔄 %s reset (content cleared).".formatted(filePath.getFileName()));
            return filePath.toFile();
        }, "%s could not be reset".formatted(path));
    }

    public static boolean isFileEmpty(File file) {
        return ioWrap(() -> Files.size(file.toPath()) == 0,
                "%s could not be read".formatted(file.getName()));
    }

    public static void writeText(File file, String content) {
        ioWrap(() -> {
            Files.writeString(file.toPath(), content, CREATE, TRUNCATE_EXISTING, WRITE);
            FrameworkLogger.debug("✍️ %s updated with new content.".formatted(file.getName()));
            return null;
        }, "%s could not be written".formatted(file.getName()));
    }

    public static int readInt(FileChannel channel) {
        return ioWrap(() -> {
            channel.position(0);
            ByteBuffer buf = ByteBuffer.allocate(32);
            int bytes = channel.read(buf);
            if (bytes <= 0) return 0;

            var content = new String(buf.array(), 0, bytes).trim();
            return content.isEmpty() ? 0 : Integer.parseInt(content);
        }, "FileChannel could not be read as int");
    }

    public static void writeInt(FileChannel channel, int value) {
        ioWrap(() -> {
            channel.truncate(0).position(0);
            channel.write(ByteBuffer.wrap(Integer.toString(value).getBytes()));
            channel.force(true);
            return null;
        }, "FileChannel could not be written with int value");
    }
}