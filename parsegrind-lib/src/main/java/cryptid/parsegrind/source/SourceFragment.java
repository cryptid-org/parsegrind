package cryptid.parsegrind.source;

import static java.util.Objects.requireNonNull;

public final class SourceFragment {
    private final int sourceFileStartLine;

    private final int sourceFileErrorLine;

    private final String lines;

    public SourceFragment(final int sourceFileStartLine, final int sourceFileErrorLine, final String lines) {
        this.sourceFileStartLine = sourceFileStartLine;
        this.sourceFileErrorLine = sourceFileErrorLine;
        this.lines = requireNonNull(lines);
    }

    public int getSourceFileStartLine() {
        return sourceFileStartLine;
    }

    public int getSourceFileErrorLine() {
        return sourceFileErrorLine;
    }

    public String getLines() {
        return lines;
    }

    @Override
    public String toString() {
        return "SourceFragment{" +
                "sourceFileStartLine=" + sourceFileStartLine +
                ", sourceFileErrorLine=" + sourceFileErrorLine +
                ", lines='" + lines + '\'' +
                '}';
    }
}
