package cryptid.parsegrind.valgrind.parser;

import cryptid.parsegrind.valgrind.model.ValgrindReport;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ValgrindParser {
    public ValgrindReport parseAgainstGlob(Path basedir, String glob) throws IOException {
        ValgrindReport valgrindReport = new ValgrindReport();

        try (final DirectoryStream<Path> stream = Files.newDirectoryStream(basedir, glob)) {
            for (final Path sourcePath : stream) {
                final String fileName = sourcePath.toString();

                try {
                    ValgrindReport report = new ValgrindSaxParser().parse(sourcePath);
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
            }
        }

        return valgrindReport;
    }
}
