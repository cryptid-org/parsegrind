package cryptid.parsegrind.valgrind;

import cryptid.parsegrind.valgrind.model.ValgrindError;
import cryptid.parsegrind.valgrind.model.ValgrindProcess;
import cryptid.parsegrind.valgrind.util.ValgrindSourceFile;

/**
 * @author Johannes Ohlemacher
 */
public class ValgrindErrorDetail {
    private ValgrindError error;
    private ValgrindProcess process;

    public ValgrindErrorDetail(ValgrindProcess process, ValgrindError error, ValgrindSourceFile valgrindSourceFile) {
        this.error = error;
        this.process = process;

        if (error != null)
            error.setSourceCode(valgrindSourceFile);
    }

    public ValgrindError getError() {
        return error;
    }

    public ValgrindProcess getProcess() {
        return process;
    }
}
