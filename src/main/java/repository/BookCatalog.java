package repository;

import model.Book;

import java.util.ArrayList;

public class BookCatalog {
    private final ArrayList<Book> booksList;

    public BookCatalog(ArrayList<Book> booksList) {
        this.booksList = booksList;
    }

    public ArrayList<Book> all() {
        return booksList;
    }

    public BookCatalog() {
        this.booksList = new ArrayList<Book>();
    }

    private int comparatorName(Book b1, Book b2, String value) {
        String a1 = b1.getName();
        String a2 = b2.getName();
        int v1 = (a1.length() - a1.replace(value, "").length()) / value.length();
        int v2 = (a2.length() - a2.replace(value, "").length()) / value.length();
        return Integer.compare(v2, v1);
    }

    private int comparatorAuthorName(Book b1, Book b2, String value) {
        String a1 = b1.getAuthorName();
        String a2 = b2.getAuthorName();
        int v1 = (a1.length() - a1.replace(value, "").length()) / value.length();
        int v2 = (a2.length() - a2.replace(value, "").length()) / value.length();
        return Integer.compare(v2, v1);
    }

    private int comparatorIsbn(Book b1, Book b2, String value) {
        String a1 = b1.getIsbn();
        String a2 = b2.getIsbn();
        int v1 = (a1.length() - a1.replace(value, "").length()) / value.length();
        int v2 = (a2.length() - a2.replace(value, "").length()) / value.length();
        return Integer.compare(v2, v1);
    }

    private int comparatorAnnotation(Book b1, Book b2, String value) {
        String a1 = b1.getAnnotation();
        String a2 = b2.getAnnotation();
        int v1 = (a1.length() - a1.replace(value, "").length()) / value.length();
        int v2 = (a2.length() - a2.replace(value, "").length()) / value.length();
        return Integer.compare(v2, v1);
    }

    public BookCatalog findBy(String by, String value) throws Exception {
        ArrayList<Book> sortedBooksList = this.booksList;

        //сортировка по количеству вхождений
        switch (by) {
            case "name":
                sortedBooksList.sort((b1, b2) -> this.comparatorName(b1, b2, value));
                break;
            case "isbn":
                sortedBooksList.sort((b1, b2) -> this.comparatorIsbn(b1, b2, value));
                break;
            case "authorName":
                sortedBooksList.sort((b1, b2) -> this.comparatorAuthorName(b1, b2, value));
                break;
            case "annotation":
                sortedBooksList.sort((b1, b2) -> this.comparatorAnnotation(b1, b2, value));
                break;
            default:
                throw new Exception("Wrong by parameter");
        }

        return new BookCatalog(sortedBooksList);
    }

    public void addRecord(Book book) {
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
