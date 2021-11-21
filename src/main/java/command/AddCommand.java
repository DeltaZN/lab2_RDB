package command;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import model.Book;
import model.Client;
import repository.BookCatalog;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.Writer;

@AllArgsConstructor
public class AddCommand implements Command {

    private final Reader reader;
    private final Writer writer;
    private final BookCatalog bookCatalog;

    @Override
    @SneakyThrows
    public void execute(Client client) {
        if (!isAuthenticated(client)) {
            writer.write("Only authenticated user can execute this command.\n");
            writer.flush();
            return;
        }
        BufferedReader br = new BufferedReader(reader);
        writer.write("Input name:\n");
        writer.flush();
        String name = br.readLine();
        writer.write("Input author's name:\n");
        writer.flush();
        String author = br.readLine();
        writer.write("Input genre:\n");
        writer.flush();
        String genre = br.readLine();
        writer.write("Input publish date:\n");
        writer.flush();
        String publishDate = br.readLine();
        writer.write("Input annotation:\n");
        writer.flush();
        String annotation = br.readLine();
        writer.write("Input ISBN:\n");
        writer.flush();
        String isbn = br.readLine();
        Book record = new Book(name, author, genre, publishDate, annotation, isbn, client);
        if (bookCatalog.addRecord(record)) {
            writer.write("The book was saved successfully\n");
        } else {
            writer.write("An error occurred during persisting the book.\n");
        }
        writer.flush();
    }
}