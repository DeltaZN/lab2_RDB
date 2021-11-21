package command;

import model.Client;

public interface Command {
    void execute(Client client);
    default boolean isAuthenticated(Client client) {
        return client != null;
    }
}
