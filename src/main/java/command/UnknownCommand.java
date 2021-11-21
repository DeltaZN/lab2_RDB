package command;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import model.Client;

import java.io.Writer;

@AllArgsConstructor
public class UnknownCommand implements Command {

    private Writer writer;

    @SneakyThrows
    @Override
    public void execute(Client client) {
        writer.write("Unknown command.\n");
    }
}
