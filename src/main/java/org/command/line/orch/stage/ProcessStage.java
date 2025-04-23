package org.command.line.orch.stage;

import org.command.line.orch.context.TerminalErrorHandler;
import org.command.line.orch.context.TerminalExecutionContext;
import org.command.line.orch.output.TerminalOutput;

import java.util.Collections;
import java.util.List;

public interface ProcessStage {

    default void preExecute(TerminalExecutionContext ctx) {
    }

    TerminalOutput execute(TerminalExecutionContext ctx);

    default void postExecute(TerminalExecutionContext ctx) {
    }

    default boolean proceed() {
        return statusCode() != null && successCodes() != null && successCodes().contains(statusCode());
    }

    void setStatusCode(int code);

    Integer statusCode();


    /**
     * Alternate success code the process stage should consider
     *
     * @return
     */
    default List<Integer> successCodes() {
        return Collections.singletonList(0);
    }

    default void setErrorHandler(TerminalErrorHandler err) {
    }

    /**
     * This method is invoked when
     * 1. An error is encountered when running the terminal process
     * 2. The terminal process completed successfully and returned a non-zero error code
     *
     * @param ctx The execution context
     * @param t   the error the occurred when the process completed exceptionally, if the process did not complete exceptionally this will be null
     */
    void handleError(TerminalExecutionContext ctx, Throwable t);

}
