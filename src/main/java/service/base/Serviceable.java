package service.base;

import network.actions.RequestAction;

public interface Serviceable {
    void ready();
    void start();
    void running();
    void processing(RequestAction requestAction);
    void processed(RequestAction requestAction);
    void stop();
}
