package ru.itmo;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import repository.BookCatalog;

import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        BookCatalog bookCatalog;
        int port = 4004;
        try {
            String filename = "books.json";
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(filename));
            bookCatalog = gson.fromJson(reader, BookCatalog.class);
            if (bookCatalog == null) {
                bookCatalog = new BookCatalog();
            }
        } catch (Exception e) {
            e.printStackTrace();
            bookCatalog = new BookCatalog();
        }
        Server server = new Server(bookCatalog);
        server.start(port);
    }
}
