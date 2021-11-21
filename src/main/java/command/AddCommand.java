package command;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import model.Book;
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
    public void execute() {
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
        Book record = new Book(name, author, genre, publishDate, annotation, isbn);
        bookCatalog.addRecord(record);
    }
}