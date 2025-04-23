package org.command.line.orch.stage;

import org.command.line.orch.context.TerminalExecutionContext;
import org.command.line.orch.model.Command;
import org.command.line.orch.output.DefaultTerminalOutput;
import org.command.line.orch.output.TerminalOutput;
import org.command.line.orch.process.ProcessResult;
import org.command.line.orch.process.TerminalProcess;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class DefaultProcessStage implements ProcessStage {

    private final Command cmd;

    private Integer statusCd;

    private List<Integer> successCodes;

    private DefaultProcessStage(DefaultProcessStageBuilder builder) {
        this.cmd = builder.cmd;
        this.successCodes = builder.successCodes;
    }

    public static DefaultProcessStageBuilder builder() {
        return new DefaultProcessStageBuilder();
    }

    @Override
    public List<Integer> successCodes() {
        if (this.successCodes == null) Collections.singletonList(0);
        return successCodes;
    }

    @Override
    public TerminalOutput execute(TerminalExecutionContext ctx) {

        TerminalProcess tp = TerminalProcess.builder().cmd(this.cmd).build();
        try {
            ProcessResult output = tp.exec(ctx);
            TerminalOutput terminalOutput = DefaultTerminalOutput.builder().cmd(this.cmd)
                    .stnd(output.getOut())
                    .exitCode(output.getExitCode())
                    .completedSuccessfully(this.successCodes().contains(output.getExitCode()))
                    .build();
            return terminalOutput;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setStatusCode(int code) {
        this.statusCd = code;
    }

    @Override
    public Integer statusCode() {
        return statusCd;
    }

    @Override
    public void handleError(TerminalExecutionContext ctx, Throwable t) {
        //BY Default the error handler is a NOOP
    }

    public static class DefaultProcessStageBuilder {
        private Command cmd;

        private List<Integer> successCodes;

        private DefaultProcessStageBuilder() {
        }

        public DefaultProcessStageBuilder cmd(Command cmd) {
            this.cmd = cmd;
            return this;
        }

        public DefaultProcessStageBuilder successCodes(List<Integer> successCodes) {
            this.successCodes = successCodes;
            return this;
        }

        public DefaultProcessStage build() {
            return new DefaultProcessStage(this);
        }
    }
}
