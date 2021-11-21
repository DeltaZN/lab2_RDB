package command;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import model.Client;
import repository.BookCatalog;
import util.Pair;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.Writer;
import java.util.function.Function;

@AllArgsConstructor
public class RegisterCommand implements Command {

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
        Pair<String, Client> pair = bookCatalog.register(login);
        if (pair.second != null) {
            writer.write("Successfully registered and logged in!\n");
        } else {
            writer.write(String.format("%s\n", pair.first));
        }
        writer.flush();
        callback.apply(pair.second);
    }
}
