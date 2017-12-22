package network.actions;

public abstract class BaseAction {

    protected int topid;
    protected String sid;

    public abstract void pack();

    public abstract void unpack();

    public abstract Integer getCode();
}
