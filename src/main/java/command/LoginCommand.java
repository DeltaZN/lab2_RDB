package command;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import model.Client;
import repository.BookCatalog;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.Writer;
import java.util.function.Function;
import java.util.function.Supplier;

@AllArgsConstructor
public class LoginCommand implements Command {

    private final Reader reader;
    private final Writer writer;
    private final BookCatalog bookCatalog;
    private final Function<Client, Client> callback;

    @SneakyThrows
    @Override
    public void execute(Client maybeClient) {
        if (isAuthenticated(maybeClient)) {
            writer.write("You are already logged in.");
            writer.flush();
            return;
        }
        BufferedReader br = new BufferedReader(reader);
        writer.write("Input login\n");
        writer.flush();
        String login = br.readLine();
        Client client = bookCatalog.authenticate(login);
        if (client != null) {
            writer.write("Successfully logged in!\n");
        } else {
            writer.write("Login failed.\n");
        }
        writer.flush();
        callback.apply(client);
    }
}
