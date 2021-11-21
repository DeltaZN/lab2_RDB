package command;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.Writer;

@AllArgsConstructor
public class HelpCommand implements Command {

    private final Writer writer;

    @SneakyThrows
    @Override
    public void execute() {
        writer.write("Enter the number of action and press [Enter]. Then follow instructions.\n");
        writer.write("Menu:\n");
        writer.write("1. list - view all the books\n");
        writer.write("2. search - search the by its title, author's name, etc...\n");
        writer.write("3. add - add a new book\n");
        writer.write("4. help - print this message again\n");
        writer.write("5. exit\n");
        writer.flush();
    }
}

