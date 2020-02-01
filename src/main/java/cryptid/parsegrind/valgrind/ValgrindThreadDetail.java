package cryptid.parsegrind.valgrind;

import cryptid.parsegrind.valgrind.model.ValgrindProcess;
import cryptid.parsegrind.valgrind.model.ValgrindThread;
import cryptid.parsegrind.valgrind.util.ValgrindSourceFile;

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
