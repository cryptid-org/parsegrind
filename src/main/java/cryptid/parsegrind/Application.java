package cryptid.parsegrind;

import cryptid.parsegrind.source.SourceReader;
import cryptid.parsegrind.valgrind.ValgrindPublisher;
import cryptid.parsegrind.valgrind.model.ValgrindReport;
import cryptid.parsegrind.valgrind.render.ValgrindReportRenderer;
import j2html.tags.Tag;
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
    public static void main(String[] args) throws Exception {
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
        final Configuration configuration = configurationFromApplication();

        final ValgrindPublisher valgrindPublisher = new ValgrindPublisher();
        try {
            final ValgrindReport report = valgrindPublisher.perform(configuration);

            final SourceReader sourceReader = new SourceReader(configuration);

            final ValgrindReportRenderer renderer = new ValgrindReportRenderer(configuration, report, sourceReader);

            final Tag document = renderer.render();

            final String renderedDocument = document.render();

            writeOutput(renderedDocument, configuration.outputFile);
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
        final Configuration configuration = new Configuration();

        configuration.baseDirectory = Optional.ofNullable(baseDirectory)
                .map(Paths::get)
                .map(Path::toAbsolutePath)
                .map(Path::normalize)
                .map(Path::toString)
                .orElseGet(this::getCurrentDirectory);
        configuration.xmlGlobs = xmlGlobs;
        configuration.sourceGlobs = sourceGlobs;
        configuration.linesBefore = linesBefore;
        configuration.linesAfter = linesAfter;
        configuration.outputFile = outputFile;
        configuration.repositoryBaseLink = repositoryBaseLink;

        return configuration;
    }

    private String getCurrentDirectory() {
        return Paths.get("").toAbsolutePath().toString();
    }
}
