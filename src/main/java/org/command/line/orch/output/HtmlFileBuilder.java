package org.command.line.orch.output;

import org.command.line.orch.context.TerminalExecutionContext;

import java.io.*;
import java.time.Instant;
import java.util.Optional;

public class HtmlFileBuilder {

    String path;

    ClassLoader classLoader = this.getClass().getClassLoader();

    private HtmlFileBuilder(String path) {
        this.path = path;
    }


    public static Optional<HtmlFileBuilder> from(String path) {
        if (path == null || path.isBlank()) return Optional.empty();

        File file = new File(path);
        boolean directory = file.isDirectory();

        if (!directory) return Optional.empty();

        return Optional.ofNullable(new HtmlFileBuilder(path));
    }


    public void persistHtmlFileOutput(TerminalExecutionContext ctx, int requests) {
        if (ctx == null || ctx.output() == null || ctx.output().isEmpty()) return;
        try {
            String base = readStringFromFile("base.fragment.html");

            String temp = "";

            for (TerminalOutput to : ctx.output()) {

                String stage = readStringFromFile("stage.fragment.html");
                String replace = null;
                if (to.completedSuccessfully()) {
                    replace = readStringFromFile("success.fragment.html");
                } else {
                    replace = readStringFromFile("failure.fragment.html");
                }
                stage = stage.replace("{{success_or_failure}}", replace);
                stage = stage.replace("{{cmd}}", to.fullCmd());
                stage = stage.replace("{{cmd_output}}", to.stndOutput());
                stage = stage.replace("{{exit_code}}", Integer.toString(to.exitCode()));

                temp += stage;
                temp += "<br/><br/><br/>";
            }

            base = base.replace("{{stages}}", temp);
            base = base.replace("{{completed_processes}}", Integer.toString(ctx.output().size()));
            base = base.replace("{{requested_processes}}", Integer.toString(requests));


            String uniqueSuffix = "";

            if (path.endsWith("\\")) {
                uniqueSuffix = "execution_" + Instant.now().toString() + ".html";
            } else {
                uniqueSuffix = "\\execution_" + Instant.now().toString() + ".html";
            }

            uniqueSuffix = uniqueSuffix.replace(":", "");
            uniqueSuffix = uniqueSuffix.replace("-", "_");


            File file = new File(this.path + uniqueSuffix);
            file.createNewFile();

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.append(base);
            fileWriter.flush();
            fileWriter.close();
        } catch (Throwable t0) {
            t0.printStackTrace();
        }
    }

    private String readStringFromFile(String s) {
        try {
            File file = new File(classLoader.getResource(s).getFile());
            InputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            char[] charArray = new char[(int) file.length()];
            inputStreamReader.read(charArray);

            return new String(charArray);
        } catch (Exception e0) {
            e0.printStackTrace();
        }

        return "";
    }
}
