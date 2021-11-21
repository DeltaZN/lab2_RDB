package command;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import repository.BookCatalog;

import java.io.Writer;

@AllArgsConstructor
public class ListCommand implements Command {

    private final Writer writer;
    private final BookCatalog bookCatalog;

    @SneakyThrows
    @Override
    public void execute() {
        writer.write("All compositions in catalog:\n");
        writer.write(String.format("%s\n", bookCatalog.toString()));
        writer.flush();
    }
}
