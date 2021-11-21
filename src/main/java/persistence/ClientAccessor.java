package persistence;

import model.Client;

public interface ClientAccessor {
    Client findClientByName(String name);
    boolean register(String name, String password);
}
