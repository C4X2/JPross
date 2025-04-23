package org.command.line.orch;

import org.command.line.orch.context.TerminalExecutionContext;
import org.command.line.orch.stage.ProcessStage;

public interface ProcessOrchestrator {

    void addProcessStage(ProcessStage s);

    void execute();

    TerminalExecutionContext getContext();
}
