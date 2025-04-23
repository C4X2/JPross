package org.command.line.orch.context;

import org.command.line.orch.output.TerminalOutput;
import org.command.line.orch.utils.Platform;
import org.command.line.orch.utils.PlatformDetectionUtil;

import java.util.ArrayList;
import java.util.List;

public class DefaultTerminalExecutionContext implements TerminalExecutionContext {

    String outputPath;

    List<TerminalOutput> terminalProcessOutputs;

    private DefaultTerminalExecutionContext(DefaultTerminalExecutionContextBuilder defaultTerminalExecutionContextBuilder) {
        this.outputPath = defaultTerminalExecutionContextBuilder.outputPath;
        this.terminalProcessOutputs = new ArrayList<TerminalOutput>();
    }

    public static DefaultTerminalExecutionContextBuilder builder() {
        return new DefaultTerminalExecutionContextBuilder();
    }


    @Override
    public Platform getPlatform() {
        return PlatformDetectionUtil.determinePlatform();
    }

    @Override
    public TerminalProcessOutputType outputType() {
        if (outputPath != null) return TerminalProcessOutputType.FILE;
        return TerminalProcessOutputType.TERMINAL;
    }

    @Override
    public String outputTypeAsString() {
        return outputType().toString();
    }

    @Override
    public List<TerminalOutput> output() {
        return this.terminalProcessOutputs;
    }

    @Override
    public String outputLocation() {
        return this.outputPath;
    }

    @Override
    public void resetExecutions() {
        if (this.terminalProcessOutputs == null) this.terminalProcessOutputs = new ArrayList<>();
        this.terminalProcessOutputs.clear();
    }

    @Override
    public void accept(TerminalOutput terminalOutput) {
        if (terminalOutput == null) return;
        this.terminalProcessOutputs.add(terminalOutput);
    }

    public static class DefaultTerminalExecutionContextBuilder {
        String outputPath;

        public DefaultTerminalExecutionContextBuilder outputPath(String path) {
            this.outputPath = path;
            return this;
        }

/*        public DefaultTerminalExecutionContextBuilder terminalProcessOutput(TerminalProcessOutput terminalProcessOutput) {
            this.terminalProcessOutput = terminalProcessOutput;
            return this;
        }*/

        public DefaultTerminalExecutionContext build() {
            return new DefaultTerminalExecutionContext(this);
        }
    }
}
