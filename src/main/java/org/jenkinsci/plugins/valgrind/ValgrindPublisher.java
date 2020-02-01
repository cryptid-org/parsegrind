package org.jenkinsci.plugins.valgrind;

import org.jenkinsci.plugins.valgrind.model.ValgrindProcess;
import org.jenkinsci.plugins.valgrind.model.ValgrindReport;
import org.jenkinsci.plugins.valgrind.parser.ValgrindParser;

import java.io.IOException;
import java.util.Collections;

/**
 * @author Johannes Ohlemacher
 */
public class ValgrindPublisher {
    public void perform() throws IOException {
        final String pattern = "";
        ValgrindParser parser = new ValgrindParser();

        ValgrindResult valgrindResult = new ValgrindResult();
        ValgrindReport valgrindReport = parser.parseAgainstGlob(null, null);

        //remove workspace path from executable name
        if (valgrindReport.getProcesses() != null) {
            String workspacePath = "";

            for (ValgrindProcess p : valgrindReport.getProcesses()) {
                if (!p.isValid())
                    continue;

                if (p.getExecutable().startsWith(workspacePath))
                    p.setExecutable(p.getExecutable().substring(workspacePath.length()));

                if (p.getExecutable().startsWith("./"))
                    p.setExecutable(p.getExecutable().substring(2));
            }
        }

        valgrindResult.setSourceFiles(Collections.<String, String>emptyMap());
    }
}
