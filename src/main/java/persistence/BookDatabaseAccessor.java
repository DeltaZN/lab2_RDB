package persistence;

import model.Book;
import model.Client;

import java.util.Collection;

public interface BookDatabaseAccessor {
    boolean save(Book book);
    Collection<Book> findAll(int clientId);
    void shutdown();
}
