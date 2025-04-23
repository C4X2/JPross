package org.command.line.orch;

import org.command.line.orch.context.TerminalExecutionContext;
import org.command.line.orch.output.HtmlFileBuilder;
import org.command.line.orch.output.TerminalOutput;
import org.command.line.orch.stage.ProcessStage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DefaultProcessOrchestrator implements ProcessOrchestrator {

    private final List<ProcessStage> stages;
    private TerminalExecutionContext ctx;
    private Function<? super TerminalExecutionContext, Void> postExecute;

    private DefaultProcessOrchestrator(DefaultProcessOrchestratorBuilder defaultProcessOrchestrateBuilder) {
        this.stages = new ArrayList<>();
        this.ctx = defaultProcessOrchestrateBuilder.ctx;
    }

    public static DefaultProcessOrchestratorBuilder builder() {
        return new DefaultProcessOrchestratorBuilder();
    }

    @Override
    public void addProcessStage(ProcessStage s) {
        if (s == null) return;
        this.stages.add(s);
    }

    @Override
    public void execute() {

        for (ProcessStage p : stages) {
            p.preExecute(ctx);
            try {
                TerminalOutput to = p.execute(ctx);
                ctx.accept(to);
                p.setStatusCode(to.exitCode());
            } catch (Throwable t0) {
                p.handleError(ctx, t0);
            }

            if (p.statusCode() != null && p.successCodes() != null && !p.successCodes().contains(p.statusCode())) {
                p.handleError(ctx, null);
            }

            p.postExecute(ctx);

            if (!p.proceed()) {
                break;
            }
        }
        if (postExecute != null) {
            this.postExecute.apply(ctx);
        }

        Optional<HtmlFileBuilder> builder = HtmlFileBuilder.from(ctx.outputLocation());
        if (builder.isPresent()) {
            builder.get().persistHtmlFileOutput(ctx, stages.size());
        }

        cleanup();
    }

    /**
     * Resetting the TerminalOuput, so that if someone calls execute for a second time, it is a fresh start
     */
    private void cleanup() {
        this.ctx.resetExecutions();
    }

    @Override
    public TerminalExecutionContext getContext() {
        return ctx;
    }

    public static final class DefaultProcessOrchestratorBuilder {
        private TerminalExecutionContext ctx;
        private Function postExecute;

        private DefaultProcessOrchestratorBuilder() {
        }

        public DefaultProcessOrchestratorBuilder executionContext(TerminalExecutionContext ctx) {
            this.ctx = ctx;
            return this;
        }

        public DefaultProcessOrchestratorBuilder postExecute(Function<? super TerminalExecutionContext, Void> fn) {
            this.postExecute = fn;
            return this;
        }

        public DefaultProcessOrchestrator build() {
            if (ctx == null) throw new NullPointerException("TerminalExecutionContext was null");
            return new DefaultProcessOrchestrator(this);
        }
    }
}
