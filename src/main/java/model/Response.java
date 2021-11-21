package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response {
    private final String message;
    @Override
    public String toString() {
        return "Ответ сервера:" + message + '\n';
    }
}
