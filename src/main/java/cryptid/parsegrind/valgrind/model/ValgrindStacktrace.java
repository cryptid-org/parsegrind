package cryptid.parsegrind.valgrind.model;

import cryptid.parsegrind.valgrind.util.ValgrindSourceFile;

import java.util.ArrayList;
import java.util.List;

public class ValgrindStacktrace {
    private List<ValgrindStacktraceFrame> frames;

    public void setSourceCode(ValgrindSourceFile sourceFile) {
        if (frames == null)
            return;

        for (ValgrindStacktraceFrame frame : frames) {
            if (frame == null)
                continue;

            frame.setSourceCode(sourceFile.getSnippet(frame.getFilePath(), frame.getLineNumber()));
        }
    }

    public String toString() {
        if (frames == null)
            return "";

        StringBuffer buf = new StringBuffer();
        for (ValgrindStacktraceFrame frame : frames) {
            buf.append(frame.toString() + "\n\n");
        }
        return buf.toString();
    }

    public String getFileSummary() {
        if (frames == null)
            throw new IllegalStateException("valgrind stacktrace is empty");

        List<String> files = new ArrayList<String>();

        for (ValgrindStacktraceFrame frame : frames)
            files.add(frame.getFileName() + "(" + frame.getLineNumber() + ")");

        return String.join(", ", files);
    }

    public void addFrame(ValgrindStacktraceFrame frame) {
        if (frames == null)
            frames = new ArrayList<ValgrindStacktraceFrame>();

        frames.add(frame);
    }

    public int size() {
        if (frames == null)
            return 0;

        return frames.size();
    }

    public boolean isEmpty() {
        return (frames == null || frames.isEmpty());
    }

    public ValgrindStacktraceFrame getFrame(int index) {
        if (isEmpty() || frames == null)
            throw new IllegalStateException("valgrind stacktrace is empty");

        return frames.get(index);
    }

    public List<ValgrindStacktraceFrame> getFrames() {
        return frames;
    }

    public void setFrames(List<ValgrindStacktraceFrame> frames) {
        this.frames = frames;
    }

}
