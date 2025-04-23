package org.command.line.orch.context;

import org.command.line.orch.output.TerminalOutput;

public interface TerminalErrorHandler {
    boolean handlerError(TerminalExecutionContext ctx, TerminalOutput to);
}
