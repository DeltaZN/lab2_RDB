package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private String name;
    private String authorName;
    private String genre;
    private String publishDate;
    private String annotation;
    private String isbn;
}
