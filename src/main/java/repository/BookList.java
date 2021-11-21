package repository;

import model.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class BookList {
    ArrayList<Book> booksList;

    public BookList(ArrayList<Book> booksList) {
        this.booksList = booksList;
    }

    public ArrayList<Book> all() {
        return booksList;
    }

    public BookList() {
        this.booksList = new ArrayList<Book>();
    }

    private int comparatorName(Book b1, Book b2, String value) {
        String a1 = b1.getName();
        String a2 = b2.getName();
        int v1 = (a1.length() - a1.replace(value, "").length()) / value.length();
        int v2 = (a2.length() - a2.replace(value, "").length()) / value.length();
        if (v1 > v2) return -1;
        else if (v1 < v2) return 1;
        else return 0;
    }

    private int comparatorAuthorName(Book b1, Book b2, String value) {
        String a1 = b1.getAuthorName();
        String a2 = b2.getAuthorName();
        int v1 = (a1.length() - a1.replace(value, "").length()) / value.length();
        int v2 = (a2.length() - a2.replace(value, "").length()) / value.length();
        if (v1 > v2) return -1;
        else if (v1 < v2) return 1;
        else return 0;
    }

    private int comparatorIsbn(Book b1, Book b2, String value) {
        String a1 = b1.getIsbn();
        String a2 = b2.getIsbn();
        int v1 = (a1.length() - a1.replace(value, "").length()) / value.length();
        int v2 = (a2.length() - a2.replace(value, "").length()) / value.length();
        if (v1 > v2) return -1;
        else if (v1 < v2) return 1;
        else return 0;
    }

    private int comparatorAnnotation(Book b1, Book b2, String value) {
        String a1 = b1.getAnnotation();
        String a2 = b2.getAnnotation();
        int v1 = (a1.length() - a1.replace(value, "").length()) / value.length();
        int v2 = (a2.length() - a2.replace(value, "").length()) / value.length();
        if (v1 > v2) return -1;
        else if (v1 < v2) return 1;
        else return 0;
    }

    public BookList findBy(String by, String value) throws Exception {
        ArrayList<Book> sortedBooksList = this.booksList;

        //сортировка по количеству вхождений
        if (by.equals("name")) {
            sortedBooksList.sort((b1, b2) -> this.comparatorName(b1, b2, value));

        } else if (by.equals("isbn")) {
            sortedBooksList.sort((b1, b2) -> this.comparatorIsbn(b1, b2, value));

        } else if (by.equals("authorName")) {
            sortedBooksList.sort((b1, b2) -> this.comparatorAuthorName(b1, b2, value));

        } else if (by.equals("annotation")) {
            sortedBooksList.sort((b1, b2) -> this.comparatorAnnotation(b1, b2, value));

        } else throw new Exception("Wrong by parameter");

        return new BookList(sortedBooksList);
    }

    ;

    public void addBook(Book book) {
        booksList.add(book);
    }


    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Book b : booksList) {
            output.append("Название: ").append(b.getName()).append("\nИмя автора: ").append(b.getAuthorName()).append("\nЖанр: ").append(b.getGenre()).append("\nДата публикации: ").append(b.getPublishDate()).append("\nISBN: ").append(b.getIsbn()).append("\n\n");
        }
        return output.toString();
    }
}
