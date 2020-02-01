package org.jenkinsci.plugins.valgrind;

import org.jenkinsci.plugins.valgrind.model.ValgrindProcess;
import org.jenkinsci.plugins.valgrind.model.ValgrindThread;
import org.jenkinsci.plugins.valgrind.util.ValgrindSourceFile;

/**
 * @author Johannes Ohlemacher
 */
public class ValgrindThreadDetail {
    private ValgrindThread thread;
    private ValgrindProcess process;

    public ValgrindThreadDetail(ValgrindProcess process, ValgrindThread thread, ValgrindSourceFile valgrindSourceFile) {
        this.thread = thread;
        this.process = process;

        if (thread != null)
            thread.setSourceCode(valgrindSourceFile);
    }

    public ValgrindThread getThread() {
        return thread;
    }

    public ValgrindProcess getProcess() {
        return process;
    }
}
