package persistence;

import lombok.SneakyThrows;
import model.Book;
import model.Client;
import security.Security;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PostgresBookDatabaseAccessor implements AppDatabaseAccessor {
    private final Connection connection;

    @SneakyThrows
    public PostgresBookDatabaseAccessor(String address, int port, String database, String user, String password) {
        this.connection = DriverManager
                .getConnection(String.format("jdbc:postgresql://%s:%d/%s", address, port, database), user, password);
        this.initTables();
    }

    private void initTables() {
        try (Statement stmt = connection.createStatement()) {
            String clientsTableSql = "CREATE TABLE IF NOT EXISTS clients"
                    + "(client_id SERIAL PRIMARY KEY, name varchar(30) NOT NULL UNIQUE," +
                    "password text NOT NULL, salt text NOT NULL)";
            stmt.execute(clientsTableSql);

            String tableSql = "CREATE TABLE IF NOT EXISTS books"
                    + "(book_id SERIAL PRIMARY KEY, name varchar(30) UNIQUE,"
                    + "author_name varchar(30), genre varchar(30), publish_date varchar(30)," +
                    "annotation varchar(30), isbn varchar(30), client_id int NOT NULL REFERENCES clients ON DELETE CASCADE)";
            stmt.execute(tableSql);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public boolean save(Book book) {
        String insertBookSql = "INSERT INTO books(name, author_name, genre, publish_date, annotation, isbn, client_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        boolean isSuccessful = false;
        try (PreparedStatement stmt = connection.prepareStatement(insertBookSql)) {
            stmt.setString(1, book.getName());
            stmt.setString(2, book.getAuthorName());
            stmt.setString(3, book.getGenre());
            stmt.setString(4, book.getPublishDate());
            stmt.setString(5, book.getAnnotation());
            stmt.setString(6, book.getISBN());
            stmt.setInt(7, book.getClient().getId());
            stmt.execute();
            isSuccessful = true;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return isSuccessful;
    }

    @SneakyThrows
    public Client findClient(int id) {
        String selectSql = "SELECT * FROM clients WHERE client_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(selectSql)) {
            stmt.setInt(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return new Client(
                            resultSet.getInt("client_id"),
                            resultSet.getString("name"),
                            resultSet.getString("password"),
                            resultSet.getString("salt")
                    );
                } else {
                    throw new IllegalStateException("Client doesn't exists!");
                }
            }
        }
    }

    @SneakyThrows
    public Client findClientByName(String name) {
        String selectSql = "SELECT * FROM clients WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(selectSql)) {
            stmt.setString(1, name);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return new Client(
                            resultSet.getInt("client_id"),
                            name,
                            resultSet.getString("password"),
                            resultSet.getString("salt")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public boolean register(String name, String password) {
        String salt = Security.generateSalt();
        String hashedPassword = Security.hashPassword(password, salt);
        String insertBookSql = "INSERT INTO clients(name, password, salt) VALUES (?, ?, ?)";
        boolean isSuccessful = false;
        try (PreparedStatement stmt = connection.prepareStatement(insertBookSql)) {
            stmt.setString(1, name);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, salt);
            stmt.execute();
            isSuccessful = true;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return isSuccessful;
    }

    @Override
    public Collection<Book> findAll(int clientId) {
        List<Book> books = new ArrayList<>();
        String selectSql = "SELECT * FROM books WHERE client_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(selectSql)) {
            stmt.setInt(1, clientId);
            Client client = findClient(clientId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    resultSet.getInt("client_id");
                    Book book = new Book(
                            resultSet.getString("name"),
                            resultSet.getString("author_name"),
                            resultSet.getString("genre"),
                            resultSet.getString("publish_date"),
                            resultSet.getString("annotation"),
                            resultSet.getString("isbn"),
                            client
                    );
                    books.add(book);
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return books;
    }

    @Override
    public void shutdown() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Connection shutdown was failed.");
        }
    }
}
