package org.jenkinsci.plugins.valgrind;

import org.jenkinsci.plugins.valgrind.model.ValgrindError;
import org.jenkinsci.plugins.valgrind.model.ValgrindProcess;
import org.jenkinsci.plugins.valgrind.model.ValgrindReport;
import org.jenkinsci.plugins.valgrind.model.ValgrindThread;
import org.jenkinsci.plugins.valgrind.util.ValgrindSourceFile;

import java.io.IOException;
import java.util.Map;


public class ValgrindResult {
    private static final String PID_TOKEN = "pid=";

    private Map<String, String> sourceFiles;

    private static final int LINES_BEFORE = 10;
    private static final int LINES_AFTER = 5;

    public ValgrindReport getReport() {
        return null;
    }

    public Map<String, String> getSourceFiles() {
        return sourceFiles;
    }

    public void setSourceFiles(Map<String, String> sourceFiles) {
        this.sourceFiles = sourceFiles;
    }

    public Object getDynamic(final String link)
            throws IOException, InterruptedException {
        final String[] s = link.split("/");
        final String data = s[s.length - 1];

        if (!data.startsWith(PID_TOKEN))
            return null;

        int sep = data.indexOf(",");

        ValgrindReport report = getReport();
        if (sep > PID_TOKEN.length()) {
            String pid = data.substring(PID_TOKEN.length(), sep);
            String uniqueId = data.substring(sep + 1);

            if (uniqueId.startsWith("tid")) {
                ValgrindThread thread = report.findThread(pid, uniqueId.substring(3));
                if (thread == null)
                    return null;

                ValgrindSourceFile sourceFile = new ValgrindSourceFile(LINES_BEFORE, LINES_AFTER, sourceFiles);

                return new ValgrindThreadDetail(report.findProcess(pid), thread, sourceFile);
            } else {
                ValgrindError error = report.findError(pid, uniqueId);
                if (error == null)
                    return null;

                ValgrindSourceFile sourceFile = new ValgrindSourceFile(LINES_BEFORE, LINES_AFTER, sourceFiles);

                return new ValgrindErrorDetail(report.findProcess(pid), error, sourceFile);
            }
        } else {
            String pid = data.substring(PID_TOKEN.length());
            ValgrindProcess process = report.findProcess(pid);

            return new ValgrindProcessDetails(process);
        }
    }
}
