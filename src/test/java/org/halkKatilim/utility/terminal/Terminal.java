package org.halkKatilim.utility.terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.halkKatilim.utility.helpers.FrameworkLogger.error;
import static org.halkKatilim.utility.helpers.FrameworkLogger.info;
import static org.halkKatilim.utility.terminal.TerminalText.*;

public class Terminal {

    private Terminal() {
    }

    public static void runShellScript(String scriptPath) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", scriptPath);
            processBuilder.inheritIO();

            System.out.println("👉 Running shell script: " + scriptPath);

            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            System.out.println("🔎 Shell script exit code: " + exitCode);

            if (exitCode != 0) {
                error(String.format(SHELL_SCRIPT_FAILED, exitCode));
                throw new RuntimeException(String.format(SHELL_SCRIPT_FAILED, exitCode));
            } else {
                info(SHELL_SCRIPT_SUCCESS);
            }
        } catch (IOException e) {
            System.err.println("❌ IOException while running shell script: " + scriptPath);
            System.err.println("   • Message: " + e.getMessage());
            e.printStackTrace();
            error(String.format(SHELL_SCRIPT_FAILED_TO_RUN, e));
            throw new RuntimeException(String.format(SHELL_SCRIPT_FAILED_TO_RUN, e));
        } catch (InterruptedException e) {
            System.err.println("❌ InterruptedException while running shell script: " + scriptPath);
            System.err.println("   • Message: " + e.getMessage());
            e.printStackTrace();
            error(String.format(SHELL_SCRIPT_FAILED_TO_RUN, e));
            throw new RuntimeException(String.format(SHELL_SCRIPT_FAILED_TO_RUN, e));
        }
    }

    public static void runShellScriptWithParameter(String scriptPath, String... parameters) {
        try {
            List<String> command = new ArrayList<>();
            command.add("bash");
            command.add(scriptPath);
            Collections.addAll(command, parameters);
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.inheritIO();
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                error(String.format(SHELL_SCRIPT_FAILED, exitCode));
                throw new RuntimeException(String.format(SHELL_SCRIPT_FAILED, exitCode));
            } else {
                info(SHELL_SCRIPT_SUCCESS);
            }
        } catch (IOException | InterruptedException e) {
            error(String.format(SHELL_SCRIPT_FAILED_TO_RUN, e));
            throw new RuntimeException(SHELL_SCRIPT_FAILED_TO_RUN, e);
        }
    }
}
