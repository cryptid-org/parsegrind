package cryptid.parsegrind.valgrind.parser;

import cryptid.parsegrind.Configuration;
import cryptid.parsegrind.valgrind.model.ValgrindReport;
import cryptid.parsegrind.valgrind.util.MultipleGlobPathFilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ValgrindParser {
    public ValgrindReport parse(final Configuration configuration) throws IOException {
        final ValgrindReport valgrindReport = new ValgrindReport();

        final MultipleGlobPathFilter filter = new MultipleGlobPathFilter(configuration.getXmlGlobs());

        Files.find(Paths.get(configuration.getBaseDirectory()),
                Integer.MAX_VALUE,
                (filePath, fileAttr) -> fileAttr.isRegularFile() && filter.accept(filePath))
                .forEach(filePath -> {
                    final String fileName = filePath.toString();

                    try {
                        ValgrindReport report = new ValgrindSaxParser().parse(filePath);
                        if (report != null && report.isValid()) {
                            valgrindReport.integrate(report);
                        } else {
                            valgrindReport.addParserError(fileName, "no valid data");
                        }
                    } catch (RuntimeException e) {
                        throw e;
                    } catch (Exception e) {
                        valgrindReport.addParserError(fileName, e.getMessage());
                    }
                });

        return valgrindReport;
    }
}
