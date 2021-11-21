package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Book {
    private String name;
    private String authorName;
    private String genre;
    private String publishDate;
    private String annotation;
    private String ISBN;
    private Client client;
}
