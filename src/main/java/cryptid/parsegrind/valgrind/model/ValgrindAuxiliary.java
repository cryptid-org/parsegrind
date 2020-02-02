package cryptid.parsegrind.valgrind.model;

public class ValgrindAuxiliary {
    private String description;
    private ValgrindStacktrace stacktrace;

    public ValgrindStacktrace getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(ValgrindStacktrace stacktrace) {
        this.stacktrace = stacktrace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
