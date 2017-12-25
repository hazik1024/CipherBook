package network.socket;

import network.actions.RequestAction;

public interface WriteDatable {
    void writed(Integer type);
    void addSendAction(RequestAction action);
}
