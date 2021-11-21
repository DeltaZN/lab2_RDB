package ru.itmo;

import command.*;
import lombok.SneakyThrows;
import model.Client;
import repository.BookCatalog;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;

public class CommandHandler {
    private final Reader reader;
    private final Writer writer;
    private final BookCatalog bookCatalog;
    private boolean isRunning = true;
    private final HashMap<String, Command> availableCommands = new HashMap<>();
    private Command unknownCommand;
    private Client client = null;

    public CommandHandler(Reader reader, Writer writer, BookCatalog bookCatalog) {
        this.reader = reader;
        this.writer = writer;
        this.bookCatalog = bookCatalog;
        this.registerDefaultCommands();
    }

    @SneakyThrows
    public void handleCommands() {
        BufferedReader reader = new BufferedReader(this.reader);
        new HelpCommand(writer).execute(client);
        while (isRunning) {
            writer.write("Input the command:\n");
            writer.flush();
            String cmd = reader.readLine().toLowerCase();
            Command command = getCmdFromStr(cmd);
            command.execute(client);
            writer.write("----\n");
            writer.flush();
        }
    }

    private void registerDefaultCommands() {
        unknownCommand = new UnknownCommand(writer);
        registerCommandType("list", new ListCommand(writer, bookCatalog));
        registerCommandType("add", new AddCommand(reader, writer, bookCatalog));
        registerCommandType("search", new SearchCommand(reader, writer, bookCatalog));
        registerCommandType("exit", new ExitCommand(writer, () -> isRunning = false));
        registerCommandType("help", new HelpCommand(writer));
        registerCommandType("login", new LoginCommand(reader, writer, bookCatalog, client -> this.client = client));
        registerCommandType("register", new RegisterCommand(reader, writer, bookCatalog, client -> this.client = client));
        registerCommandType("logout", new LogoutCommand(writer, () -> this.client = null));
    }

    public void registerCommandType(String name, Command command) {
        availableCommands.put(name, command);
    }

    private Command getCmdFromStr(String cmd) {
        Command command = availableCommands.get(cmd);
        if (command == null) {
            command = unknownCommand;
        }
        return command;
    }
}