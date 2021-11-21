package ru.itmo;

import persistence.PostgresBookDatabaseAccessor;
import repository.BookCatalog;

public class Main {
    public static void main(String[] args) {
        BookCatalog bookCatalog;
        int port = 4004;
        PostgresBookDatabaseAccessor accessor = new PostgresBookDatabaseAccessor("localhost", 5432, "rdb", "postgres", "1234");
        bookCatalog = new BookCatalog(accessor);
        Server server = new Server(bookCatalog);
        server.start(port);
    }
}
