package network.socket;

import network.actions.BaseAction;

public interface WriteDatable {
    void writed(Integer type);
    void addSendAction(BaseAction action);
}
