package network.socket;

import actions.RequestAction;

public interface WriteDatable {
    void writed(Integer type);
    void addSendAction(RequestAction action);
}
