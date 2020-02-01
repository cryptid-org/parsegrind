package cryptid.parsegrind;

import cryptid.parsegrind.valgrind.ValgrindPublisher;
import picocli.CommandLine;

import java.nio.file.Paths;
import java.util.List;

import static java.util.Objects.requireNonNullElseGet;

@CommandLine.Command(
        name = "parsegrind",
        mixinStandardHelpOptions = true,
        version = "1.0.0")
public class Application implements Runnable {
    public static void main(String[] args) throws Exception {
        new CommandLine(new Application()).execute(args);
    }

    @CommandLine.Option(names = { "--source-glob" },
            description = "Glob pattern to use when matching source files.",
            required = true)
    public List<String> sourceGlobs;

    @CommandLine.Option(names = { "--valgrind-glob" },
            description = "Glob pattern to use when matching valgrind output files.",
            required = true)
    public List<String> xmlGlobs;

    @CommandLine.Option(names = { "--base-directory" },
            description = "The workspace directory. When omitted, the current directory will be used.")
    public String baseDirectory;

    @CommandLine.Option(names = { "--lines-before" },
            description = "Number of lines to output before an offending line.",
            defaultValue = "5")
    public int linesBefore;

    @CommandLine.Option(names = { "--lines-after" },
            description = "Number of lines to output after an offending line.",
            defaultValue = "5")
    public int linesAfter;

    @Override
    public void run() {
        final Configuration configuration = configurationFromApplication();

        final ValgrindPublisher valgrindPublisher = new ValgrindPublisher();
        try {
            valgrindPublisher.perform(configuration);
        } catch (final Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private Configuration configurationFromApplication() {
        final Configuration configuration = new Configuration();

        configuration.baseDirectory = requireNonNullElseGet(baseDirectory, this::getCurrentDirectory);
        configuration.xmlGlobs = xmlGlobs;
        configuration.sourceGlobs = sourceGlobs;
        configuration.linesBefore = linesBefore;
        configuration.linesAfter = linesAfter;

        return configuration;
    }

    private String getCurrentDirectory() {
        return Paths.get("").toAbsolutePath().toString();
    }
}
