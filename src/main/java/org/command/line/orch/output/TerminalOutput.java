package org.command.line.orch.output;

public interface TerminalOutput {

    String fullCmd();

    int exitCode();

    String stndOutput();

    boolean completedSuccessfully();
}
