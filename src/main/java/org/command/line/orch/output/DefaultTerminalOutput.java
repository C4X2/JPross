package org.command.line.orch.output;

import org.command.line.orch.model.Command;

public class DefaultTerminalOutput implements TerminalOutput {
    Command cmd;

    Integer exitCode;

    String standardOut;

    boolean completedSuccessfully;

    private DefaultTerminalOutput(DefaultTerminalOutputBuilder builder) {
        this.cmd = builder.cmd;
        this.exitCode = builder.exitCode;
        this.standardOut = builder.standardOut;
        this.completedSuccessfully = builder.completedSuccessfully;
    }

    public static DefaultTerminalOutputBuilder builder() {
        return new DefaultTerminalOutputBuilder();
    }

    @Override
    public String fullCmd() {
        return cmd.getCommand() + " " + cmd.getFlags();
    }

    @Override
    public int exitCode() {
        if (this.exitCode == null) return 0;
        return this.exitCode;
    }

    @Override
    public String stndOutput() {
        return this.standardOut;
    }

    @Override
    public boolean completedSuccessfully() {
        return this.completedSuccessfully;
    }

    public static class DefaultTerminalOutputBuilder {

        Command cmd;

        Integer exitCode;

        String standardOut;

        boolean completedSuccessfully;

        private DefaultTerminalOutputBuilder() {
        }

        public DefaultTerminalOutputBuilder cmd(Command cmd) {
            this.cmd = cmd;
            return this;
        }

        public DefaultTerminalOutputBuilder exitCode(Integer exitCode) {
            this.exitCode = exitCode;
            return this;
        }

        public DefaultTerminalOutputBuilder stnd(String standardOut) {
            this.standardOut = standardOut;
            return this;
        }

        public DefaultTerminalOutputBuilder completedSuccessfully(boolean completedSuccessfully) {
            this.completedSuccessfully = completedSuccessfully;
            return this;
        }

        public DefaultTerminalOutput build() {
            return new DefaultTerminalOutput(this);
        }

    }
}
