package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Client {
    private int id;
    private String name;
    private String password;
    private String salt;
}
