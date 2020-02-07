package cryptid.parsegrind.source;

import cryptid.parsegrind.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class SourceReader {
    private static final String NEW_LINE = "\n";

    private final Configuration configuration;

    private final int lineCount;

    public SourceReader(final Configuration configuration) {
        this.configuration = requireNonNull(configuration);

        this.lineCount = configuration.getLinesBefore() + 1 + configuration.getLinesAfter();
    }

    public SourceFragment read(final String path, final int line) throws IOException {
        final int linesToSkip = Math.max(0, line - configuration.getLinesBefore() - 1);

        try (final Stream<String> lines = Files.lines(Paths.get(path))) {
            final String contents = lines
                    .skip(linesToSkip)
                    .limit(lineCount)
                    .collect(Collectors.joining(NEW_LINE));

            return new SourceFragment(linesToSkip + 1, line, contents);
        }
    }
}
