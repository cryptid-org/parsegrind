package cryptid.parsegrind.valgrind.model;

import cryptid.parsegrind.valgrind.util.ValgrindErrorList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValgrindReport {
    private List<ValgrindProcess> processes;
    private Map<String, String> parserErrors;

    public boolean isValid() {
        if (processes == null || processes.isEmpty())
            return false;

        for (ValgrindProcess p : processes) {
            if (!p.isValid())
                return false;
        }

        return true;
    }

    public void addProcess(ValgrindProcess process) {
        if (processes == null)
            processes = new ArrayList<ValgrindProcess>();

        processes.add(process);
    }

    public void addParserError(String filename, String errorMessage) {
        if (parserErrors == null)
            parserErrors = new HashMap<String, String>();

        parserErrors.put(filename, errorMessage);
    }

    public boolean hasParserErrors() {
        return (parserErrors != null) && !parserErrors.isEmpty();
    }

    public Map<String, String> getParserErrors() {
        return parserErrors;
    }

    public List<ValgrindError> getAllErrors() {
        final List<ValgrindError> list = new ArrayList<>();

        if (processes != null) {
            for (ValgrindProcess p : processes) {
                List<ValgrindError> l = p.getErrors();
                if (l != null)
                    list.addAll(l);
            }
        }

        return list;
    }

    public void integrate(ValgrindReport valgrindReport) {
        if (valgrindReport == null || valgrindReport.processes == null)
            return;

        if (processes == null)
            processes = new ArrayList<>();

        processes.addAll(valgrindReport.processes);
    }

    public ValgrindError findError(String pid, String uniqueId) {
        ValgrindProcess process = findProcess(pid);

        return process.findErrorByUniqueId(uniqueId);
    }

    public ValgrindProcess findProcess(String pid) {
        if (processes != null) {
            for (ValgrindProcess process : processes) {
                if (process.getPid().equals(pid)) {
                    process.setupParentChilds(processes);
                    return process;
                }
            }
        }

        ValgrindProcess process = new ValgrindProcess();
        process.setExecutable(pid);
        process.setPid(pid);

        return process;
    }

    public ValgrindErrorList getErrorList() {
        return new ValgrindErrorList(getAllErrors());
    }

    public List<ValgrindProcess> getProcesses() {
        final List<ValgrindProcess> result = new ArrayList<ValgrindProcess>();

        if (processes != null) {
            for (ValgrindProcess p : processes) {
                if (p.isValid()) {
                    result.add(p);
                }
            }
        }

        for (ValgrindProcess p : result) {
            p.setupParentChilds(processes);
        }

        return result;
    }

    public ValgrindThread findThread(String pid, String hthreadid) {
        ValgrindProcess process = findProcess(pid);

        return process.findThreadByHthreadid(hthreadid);
    }
}
