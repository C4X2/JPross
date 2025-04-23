package org.command.line.orch.process;

public class ProcessResult {
    private final String out;

    private final int exitCode;

    private ProcessResult(String out, int exitCode) {
        this.out = out;
        this.exitCode = exitCode;
    }

    public static ProcessResult from(String input, int into) {
        return new ProcessResult(input, into);
    }

    public int getExitCode() {
        return exitCode;
    }

    public String getOut() {
        return out;
    }
}
