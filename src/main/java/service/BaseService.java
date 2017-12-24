package service;

import enums.ServiceStatus;
import enums.ServiceType;
import network.actions.BaseAction;

public abstract class BaseService implements Runnable, Serviceable {

    private Thread thread;
    private ServiceStatus status;
    private ServiceType type;
    private String name;

    public BaseService(ServiceType type, String name) {
        this.name = name;
        this.type = type;
        this.status = ServiceStatus.stop;
    }

    public abstract Integer getActionCode();
    public abstract void addAction(BaseAction action);

    /*
     * Runnable
     */
    public void run() {
        this.status = ServiceStatus.ready;
        ready();
        System.out.println(this.getName() + "运行中...");
        this.status = ServiceStatus.running;
        running();
    }

    /*
     * Serviceable
     */
    public void ready() {

    }

    public void start() {
        Thread thread = new Thread(this, this.getClass().getName() + ".thread");
        thread.start();
        this.thread = thread;
        System.out.println("[" + this.getClass().getName() + ":" + this.getName() + "]启动...");
    }

    public void stop() {
        this.status = ServiceStatus.stop;
        this.thread.interrupt();
        this.thread = null;
    }

    /*
     * Getter and Setter
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }

    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }
}
