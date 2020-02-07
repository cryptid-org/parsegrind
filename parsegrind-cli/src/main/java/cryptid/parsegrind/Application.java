package cryptid.parsegrind;

import picocli.CommandLine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@CommandLine.Command(
        name = "parsegrind",
        mixinStandardHelpOptions = true,
        version = "1.0.0")
public class Application implements Runnable {
    public static void main(String[] args) {
        new CommandLine(new Application()).execute(args);
    }

    @CommandLine.Option(names = {"--source-glob"},
            description = "Glob pattern to use when matching source files.",
            required = true)
    public List<String> sourceGlobs;

    @CommandLine.Option(names = {"--valgrind-glob"},
            description = "Glob pattern to use when matching valgrind output files.",
            required = true)
    public List<String> xmlGlobs;

    @CommandLine.Option(names = {"--base-directory"},
            description = "The workspace directory. When omitted, the current directory will be used.")
    public String baseDirectory;

    @CommandLine.Option(names = {"--lines-before"},
            description = "Number of lines to output before an offending line.",
            defaultValue = "5")
    public int linesBefore;

    @CommandLine.Option(names = {"--lines-after"},
            description = "Number of lines to output after an offending line.",
            defaultValue = "5")
    public int linesAfter;

    @CommandLine.Option(names = {"-o", "--output"},
            description = "Output file into which the rendered report is written.",
            required = true)
    public String outputFile;

    @CommandLine.Option(names = {"--repository-base-link"},
            description = "Base repository link (for example, GitHub) to which the file paths will be relativized to.")
    public String repositoryBaseLink;

    @Override
    public void run() {
        final var configuration = configurationFromApplication();

        try {
            final var parsegrind = Parsegrind.fromConfiguration(configuration);

            final var report = parsegrind.parse();

            final var renderedDocument = parsegrind.render(report);

            writeOutput(renderedDocument, outputFile);
        } catch (final Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private void writeOutput(final String output, final String path) throws IOException  {
        try (final var writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(output);
        }
    }

    private Configuration configurationFromApplication() {
        final String normalizedBaseDirectory = Optional.ofNullable(baseDirectory)
                .map(Paths::get)
                .map(Path::toAbsolutePath)
                .map(Path::normalize)
                .map(Path::toString)
                .orElseGet(this::getCurrentDirectory);

        return Configuration.builder()
                .sourceGlobs(sourceGlobs)
                .xmlGlobs(xmlGlobs)
                .baseDirectory(normalizedBaseDirectory)
                .linesBefore(linesBefore)
                .linesAfter(linesAfter)
                .repositoryBaseLink(repositoryBaseLink)
                .build();
    }

    private String getCurrentDirectory() {
        return Paths.get("").toAbsolutePath().toString();
    }
}
