package network.actions;

import enums.ActionType;

public class KeepAliveAction extends BaseAction {

    @Override
    public String pack() {
        return "";
    }

    @Override
    public void unpack() {

    }

    @Override
    public Integer getTopid() {
        return ActionType.keepalive.getTopid();
    }
}
