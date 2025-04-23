package org.command.line.orch.process;

import org.command.line.orch.context.TerminalExecutionContext;
import org.command.line.orch.model.Command;

import java.io.IOException;

public class TerminalProcess {

    private final Command cmd;

    private TerminalProcess(TerminalProcessBuilder builder) {
        this.cmd = builder.cmd;
    }

    public static TerminalProcessBuilder builder() {
        return new TerminalProcessBuilder();
    }

    public String getProcessName() {
        return cmd.getCommand();
    }

    public ProcessResult exec(TerminalExecutionContext context) throws IOException {
        String execCmd = cmd.getCommand() + " " + cmd.getFlags();

        try {
            Process p = Runtime.getRuntime().exec(execCmd);
            p.waitFor();

            String stnd = new String(p.getInputStream().readAllBytes());
            String error = new String(p.getErrorStream().readAllBytes());

            String retVal = (stnd != null && !stnd.isBlank()) ? stnd : error;
            return ProcessResult.from(retVal, p.exitValue());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static class TerminalProcessBuilder {
        private Command cmd;

        public TerminalProcessBuilder cmd(Command cmd) {
            this.cmd = cmd;
            return this;
        }

        public TerminalProcess build() {
            return new TerminalProcess(this);
        }
    }


}
