package repository;

import model.Book;
import model.Client;
import persistence.AppDatabaseAccessor;
import security.Security;
import util.Pair;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class BookCatalog {
    private final CopyOnWriteArrayList<Book> booksList;
    private final AppDatabaseAccessor bookAccessor;

    public BookCatalog(AppDatabaseAccessor bookAccessor) {
        this.booksList = new CopyOnWriteArrayList<>();
        this.bookAccessor = bookAccessor;
    }

    public void loadBooks(int clientId) {
        this.booksList.clear();
        this.booksList.addAll(bookAccessor.findAll(clientId));
    }

    public Client authenticate(String name, String password) {
        Client client = bookAccessor.findClientByName(name);
        if (client != null && client.getPassword().equals(Security.hashPassword(password, client.getSalt()))) {
            return client;
        }
        return null;
    }

    public Pair<String, Client> register(String name, String password) {
        if (bookAccessor.findClientByName(name) != null) {
            return new Pair<>("User is already registered!", null);
        }
        boolean isSuccessful = bookAccessor.register(name, password);
        if (isSuccessful) {
            return new Pair<>("Successfully registered!", bookAccessor.findClientByName(name));
        } else {
            return new Pair<>("Unknown error", null);
        }
    }

    public CopyOnWriteArrayList<Book> all() {
        return booksList;
    }

    public boolean addRecord(Book book) {
        boolean isSuccessful = bookAccessor.save(book);
        if (isSuccessful) {
            booksList.add(book);
        }
        return isSuccessful;
    }


    @Override
    public String toString() {
        return booksList.stream().map(b -> String.format(
                        "Название: %s\n" +
                        "Имя автора: %s\n" +
                        "Жанр: %s\n" +
                        "Дата публикации: %s\n" +
                        "ISBN: %s\n", b.getName(), b.getAuthorName(), b.getGenre(), b.getPublishDate(), b.getISBN())).collect(Collectors.joining("\n"));
    }
}
