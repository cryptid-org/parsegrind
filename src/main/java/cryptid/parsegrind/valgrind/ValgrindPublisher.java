package cryptid.parsegrind.valgrind;

import cryptid.parsegrind.Configuration;
import cryptid.parsegrind.valgrind.model.ValgrindProcess;
import cryptid.parsegrind.valgrind.model.ValgrindReport;
import cryptid.parsegrind.valgrind.parser.ValgrindParser;

import java.io.IOException;
import java.util.Collections;

import static java.util.Objects.nonNull;

public final class ValgrindPublisher {
    public void perform(final Configuration configuration) throws IOException {
        final ValgrindParser parser = new ValgrindParser();

        final ValgrindResult valgrindResult = new ValgrindResult();
        final ValgrindReport valgrindReport = parser.parse(configuration);

        // Remove workspace path from executable name.
        if (nonNull(valgrindReport.getProcesses())) {
            for (final ValgrindProcess p : valgrindReport.getProcesses()) {
                if (!p.isValid()) {
                    continue;
                }

                if (p.getExecutable().startsWith(configuration.baseDirectory)) {
                    p.setExecutable(p.getExecutable().substring(configuration.baseDirectory.length()));
                }

                if (p.getExecutable().startsWith("./")) {
                    p.setExecutable(p.getExecutable().substring(2));
                }
            }
        }

        valgrindResult.setSourceFiles(Collections.<String, String>emptyMap());
    }
}
