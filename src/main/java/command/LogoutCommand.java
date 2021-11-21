package command;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import model.Client;

import java.io.Writer;

@AllArgsConstructor
public class LogoutCommand implements Command {
    private final Writer writer;
    private final Runnable callback;

    @SneakyThrows
    @Override
    public void execute(Client client) {
        if (!isAuthenticated(client)) {
            writer.write("You must be logged in.\n");
            return;
        }
        writer.write("Logged out.\n");
        writer.flush();
        callback.run();
    }
}
