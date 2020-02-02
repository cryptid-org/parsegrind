package cryptid.parsegrind.valgrind.render;

import cryptid.parsegrind.Configuration;
import cryptid.parsegrind.source.SourceFragment;
import cryptid.parsegrind.source.SourceReader;
import cryptid.parsegrind.valgrind.model.ValgrindError;
import cryptid.parsegrind.valgrind.model.ValgrindProcess;
import cryptid.parsegrind.valgrind.model.ValgrindReport;
import cryptid.parsegrind.valgrind.model.ValgrindStacktraceFrame;
import cryptid.parsegrind.valgrind.util.MultipleGlobPathFilter;
import j2html.tags.Tag;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static j2html.TagCreator.*;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;
import static java.util.stream.Collectors.toList;

public class ValgrindReportRenderer {
    private final Configuration configuration;

    private final ValgrindReport report;

    private final SourceReader sourceReader;

    private final MultipleGlobPathFilter sourcePathFilter;

    public ValgrindReportRenderer(final Configuration configuration, final ValgrindReport report, final SourceReader sourceReader) {
        this.configuration = requireNonNull(configuration);
        this.report = requireNonNull(report);
        this.sourceReader = requireNonNull(sourceReader);

        this.sourcePathFilter = new MultipleGlobPathFilter(configuration.sourceGlobs);
    }

    public Tag render() {
        final List<ValgrindProcess> processesWithErrors = report.getProcesses().stream()
                .filter(process -> !process.getErrors().isEmpty())
                .collect(toList());

        return html(
                head(
                        title("Valgrind Report"),
                        link()
                            .withRel("stylesheet")
                            .withHref("https://cdnjs.cloudflare.com/ajax/libs/prism/1.5.0/themes/prism.min.css"),
                        link()
                            .withRel("stylesheet")
                            .withHref("https://fonts.googleapis.com/css?family=Source+Sans+Pro&display=swap"),
                        style(loadMainStylesheet())
                ),
                body(
                        h1("Valgrind Report"),
                        renderProcessList(processesWithErrors),
                        each(processesWithErrors, this::renderProcess),
                        script()
                            .withSrc("https://cdnjs.cloudflare.com/ajax/libs/prism/1.5.0/prism.min.js"),
                        script(loadMainScript())
                )
        );
    }

    private String loadMainStylesheet() {
        return readResource("main.css");
    }

    private String loadMainScript() {
        return readResource("main.js");
    }

    private Tag renderProcessList(final List<ValgrindProcess> processesWithErrors) {
        return section(
                h2("Processes"),
                table(
                        thead(
                                tr(
                                        td("Process Name"),
                                        td("Number of Errors")
                                )
                        ),
                        tbody(
                                each(processesWithErrors, process ->
                                    tr(
                                            td(a(process.getExecutable()).withHref(idForProcess(process))),
                                            td(Integer.toString(process.getErrors().size()))
                                    )
                                )
                        )
                )
        );
    }

    private Tag renderProcess(final ValgrindProcess process) {
        return section(attrs(idForProcess(process)),
                    h2(process.getExecutable()),
                    renderErrorList(process, process.getErrors()),
                    each(process.getErrors(), error -> renderError(process, error)
                )
        );
    }

    private Tag renderErrorList(final ValgrindProcess process, final List<ValgrindError> errors) {
        return div(
                table(
                        thead(
                                tr(
                                        td("Identifier"),
                                        td("Kind"),
                                        td("Description")
                                )
                        ),
                        tbody(
                                each(errors, error ->
                                        tr(
                                                td(a(error.getUniqueId()).withHref(idForError(process, error))),
                                                td(error.getKind().toString()),
                                                td(error.getDescription())
                                        )
                                )
                        )
                )
        );
    }

    private Tag renderError(final ValgrindProcess process, final ValgrindError error) {
        return div(attrs(idForError(process, error)),
                h3(error.getUniqueId() +  " " + error.getKind()),
                div(
                        p(error.getDescription())
                ),
                div(
                        h4("Stacktrace"),
                        div(
                                each(error.getStacktrace().getFrames(), this::renderFrame)
                        )
                )
        );
    }

    private Tag renderFrame(final ValgrindStacktraceFrame frame) {
        return table(
                tbody(
                    objectRow(frame),
                    functionRow(frame),
                    fileRow(frame),
                    codeRow(frame)
                )
        );
    }

    private Tag objectRow(final ValgrindStacktraceFrame frame) {
        return tr(
                td(b("Object")),
                td(frame.getObjectName())
        );
    }

    private Tag functionRow(final ValgrindStacktraceFrame frame) {
        return tr(
                td(b("Function")),
                td(frame.getFunctionName())
        );
    }

    private Tag fileRow(final ValgrindStacktraceFrame frame) {
        return tr(
                td(b("File/Line")),
                td(requireNonNullElse(frame.getFilePathAndLine(), "Unknown location."))
        );
    }

    private Tag codeRow(final ValgrindStacktraceFrame frame) {
        final Tag code;

        if (isNull(frame.getFilePath()) || !sourcePathFilter.accept(Paths.get(frame.getFilePath()))) {
            code = b("Source code not available.");
        } else {
            code = renderCode(frame);
        }

        return tr(
                td(b("Code")),
                td(code)
        );
    }

    private Tag renderCode(final ValgrindStacktraceFrame frame) {
        try {
            final SourceFragment code = sourceReader.read(frame.getFilePath(), frame.getLineNumber());

            return renderCodeBlock(code);
        } catch (final Exception e) {
            return b("Source code not available.");
        }
    }

    private Tag renderCodeBlock(final SourceFragment code) {
        final int startLineLabel = code.getSourceFileStartLine();
        final int highlightLineIndex = code.getSourceFileErrorLine() - code.getSourceFileStartLine();
        final int highlightLineOffset = code.getSourceFileStartLine();

        return pre(
                code(code.getLines())
                        .withClass("language-c")
        ).withClass("line-numbers")
                .attr("data-start", startLineLabel)
                .attr("data-line", highlightLineIndex)
                .attr("data-line-offset", highlightLineOffset);
    }

    private String readResource(final String resource) {
        try (final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("main.css")) {
            if (isNull(inputStream)) {
                return "";
            }

            try (final InputStreamReader reader = new InputStreamReader(inputStream);
                 final var bufferedReader = new BufferedReader(reader)) {
                return bufferedReader.lines().collect(Collectors.joining("\n"));
            }
        } catch (final Exception e) {
            return "";
        }
    }

    private String idForProcess(final ValgrindProcess process) {
        return "#" + urlify(process.getExecutable());
    }

    private String idForError(final ValgrindProcess process, final ValgrindError error) {
        return "#" + urlify(process.getExecutable() + "-" + error.getUniqueId());
    }

    private String urlify(final String str) {
        return str
                .replace('/', '-')
                .replace('\\', '-')
                .replace('.', '-');
    }
}
