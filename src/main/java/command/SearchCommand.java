package command;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import model.Book;
import model.Client;
import repository.BookCatalog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SearchCommand implements Command {

    private final Reader reader;
    private final Writer writer;
    private final BookCatalog catalog;

    @Override
    @SneakyThrows
    public void execute(Client client) {
        if (!isAuthenticated(client)) {
            writer.write("Only authenticated user can execute this command.\n");
            writer.flush();
            return;
        }
        BufferedReader br = new BufferedReader(reader);
        writer.write("Input the part of the name to find books in the catalog:\n");
        writer.flush();
        String searchStr = br.readLine();
        List<String> composition = catalog.all().stream().map(Book::toString).filter(c -> c.contains(searchStr)).collect(Collectors.toList());
        if (composition.size() == 0) {
            writer.write("No books were found by this criteria.\n");
        } else {
            composition.forEach(c -> {
                try {
                    writer.write(String.format("%s\n", c));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
