import org.command.line.orch.DefaultProcessOrchestrator;
import org.command.line.orch.ProcessOrchestrator;
import org.command.line.orch.context.DefaultTerminalExecutionContext;
import org.command.line.orch.context.TerminalExecutionContext;
import org.command.line.orch.model.Command;
import org.command.line.orch.stage.DefaultProcessStage;
import org.command.line.orch.stage.ProcessStage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TerminalTestClass {

    @Test
    public void basicCommandLineTests() {
        Command cmd1 = Command.builder().command("java").flags("-version").build();
        Command cmd2 = Command.builder().command("stasher").flags("--key albertross --value bird").build();
        Command goHelp = Command.builder().command("go").flags("-help").build();

        assertNotNull(cmd1);
        assertNotNull(cmd2);

        List<Integer> codes = new ArrayList<Integer>();
        Collections.addAll(codes, 0, 1, 2, 4, 6);


        ProcessStage processStage1 = DefaultProcessStage.builder().cmd(cmd1).build();
        ProcessStage processStage2 = DefaultProcessStage.builder().cmd(cmd2).build();
        ProcessStage processStage3 = DefaultProcessStage.builder().cmd(goHelp).successCodes(codes).build();

        assertNotNull(processStage1);
        assertNotNull(processStage2);

        TerminalExecutionContext terminalExecutionContext = DefaultTerminalExecutionContext.builder().outputPath("C:\\Users\\natha\\Downloads").build();
        ProcessOrchestrator orchestrator = DefaultProcessOrchestrator.builder().executionContext(terminalExecutionContext).build();
        orchestrator.addProcessStage(processStage1);
        orchestrator.addProcessStage(processStage2);
        orchestrator.execute();

        TerminalExecutionContext terminalExecutionContext2 = DefaultTerminalExecutionContext.builder().outputPath("C:\\Users\\natha\\Downloads\\").build();
        ProcessOrchestrator orchestrator2 = DefaultProcessOrchestrator.builder().executionContext(terminalExecutionContext2).build();
        orchestrator.addProcessStage(processStage1);
        orchestrator.addProcessStage(processStage2);
        orchestrator.execute();
        orchestrator2.execute();

        orchestrator2.addProcessStage(processStage1);
        orchestrator2.addProcessStage(processStage3);
        orchestrator2.addProcessStage(processStage2);
        orchestrator2.execute();
    }
}
