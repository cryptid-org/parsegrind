package cryptid.parsegrind;

import cryptid.parsegrind.source.SourceReader;
import cryptid.parsegrind.valgrind.ValgrindPublisher;
import cryptid.parsegrind.valgrind.model.ValgrindReport;
import cryptid.parsegrind.valgrind.render.ValgrindReportRenderer;
import j2html.tags.Tag;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

public final class Parsegrind {
    private final Configuration configuration;

    public static Parsegrind fromConfiguration(final Configuration configuration) {
        requireNonNull(configuration);

        return new Parsegrind(configuration);
    }

    private Parsegrind(final Configuration configuration) {
        this.configuration = configuration;
    }

    public ValgrindReport parse() throws IOException {
        final var publisher = new ValgrindPublisher();

        return publisher.perform(configuration);
    }

    public String render(final ValgrindReport report) {
        final var sourceReader = new SourceReader(configuration);

        final var renderer = new ValgrindReportRenderer(configuration, report, sourceReader);

        final Tag reportDocument = renderer.render();

        return reportDocument.render();
    }
}
