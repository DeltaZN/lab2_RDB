package command;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import model.Client;
import repository.BookCatalog;

import java.io.Writer;

@AllArgsConstructor
public class ListCommand implements Command {

    private final Writer writer;
    private final BookCatalog bookCatalog;

    @SneakyThrows
    @Override
    public void execute(Client client) {
        if (!isAuthenticated(client)) {
            writer.write("Only authenticated user can execute this command.\n");
            writer.flush();
            return;
        }
        bookCatalog.loadBooks(client.getId());
        writer.write("All books in catalog:\n");
        writer.write(String.format("%s\n", bookCatalog));
        writer.flush();
    }
}
