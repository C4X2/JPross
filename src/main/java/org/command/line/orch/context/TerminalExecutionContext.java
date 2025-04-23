package org.command.line.orch.context;

import org.command.line.orch.output.TerminalOutput;
import org.command.line.orch.utils.Platform;

import java.util.List;

public interface TerminalExecutionContext {

    /**
     * The platform this terminal orchestration is running on
     *
     * @return A platform object, {@code Platform.UNKNOWN} if unknown
     */
    Platform getPlatform();

    /**
     * Where the output of each step should be placed. By default, this value is TERMINAL, if File is selected
     * then the process will generate a html file at that location
     */
    TerminalProcessOutputType outputType();

    String outputTypeAsString();

    List<TerminalOutput> output();

    String outputLocation();

    void accept(TerminalOutput terminalOutput);

    void resetExecutions();

    void setWorkingDirectory(String directory);

}
