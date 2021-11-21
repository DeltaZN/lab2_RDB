package model;


import lombok.Data;

@Data
public class Task {
    private String type;
    private Book book;
    private String by;
    private String value;

    public Task(String type, Book book) {
        this.type = type;
        this.book = book;
        this.by = null;
        this.value = null;
    }

    public Task(String type, String by, String value) {
        this.type = type;
        this.book = null;
        this.by = by;
        this.value = value;
    }
}
