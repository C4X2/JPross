package org.command.line.orch.model;

public final class Command {
    private final String command;

    private final String flags;

    private Command(CommandBuilder builder) {
        this.command = builder.command;
        this.flags = builder.flags;
    }

    public static CommandBuilder builder() {
        return new CommandBuilder();
    }

    public String getCommand() {
        return this.command;
    }

    public String getFlags() {
        return this.flags;
    }

    public static final class CommandBuilder {
        private String command;

        private String flags;

        private CommandBuilder() {
        }

        public CommandBuilder command(String command) {
            this.command = command;
            return this;
        }

        public CommandBuilder flags(String flags) {
            this.flags = flags;
            return this;
        }

        public Command build() {
            return new Command(this);
        }
    }
}
