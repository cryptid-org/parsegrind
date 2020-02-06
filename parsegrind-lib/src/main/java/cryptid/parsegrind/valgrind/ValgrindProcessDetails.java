package cryptid.parsegrind.valgrind;

import cryptid.parsegrind.valgrind.model.ValgrindProcess;

public class ValgrindProcessDetails {
    private ValgrindProcess process;

    public ValgrindProcessDetails(ValgrindProcess process) {

        this.process = process;

    }

    public ValgrindProcess getProcess() {
        return process;
    }
}
