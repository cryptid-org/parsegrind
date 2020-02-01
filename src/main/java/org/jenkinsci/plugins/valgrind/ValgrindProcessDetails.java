package org.jenkinsci.plugins.valgrind;

import org.jenkinsci.plugins.valgrind.model.ValgrindProcess;

public class ValgrindProcessDetails {
    private ValgrindProcess process;

    public ValgrindProcessDetails(ValgrindProcess process) {

        this.process = process;

    }

    public ValgrindProcess getProcess() {
        return process;
    }
}
