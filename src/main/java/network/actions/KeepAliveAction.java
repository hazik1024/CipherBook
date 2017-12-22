package network.actions;

import enums.ActionType;

public class KeepAliveAction extends BaseAction {

    @Override
    public void pack() {

    }

    @Override
    public void unpack() {

    }

    @Override
    public Integer getCode() {
        return ActionType.keepalive.getCode();
    }
}
