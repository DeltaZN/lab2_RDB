package command;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.Writer;

@AllArgsConstructor
public class ExitCommand implements Command {

    private Writer writer;
    private Runnable performExit;

    @SneakyThrows
    @Override
    public void execute() {
        writer.write("Exiting the program\n");
        performExit.run();
    }
}