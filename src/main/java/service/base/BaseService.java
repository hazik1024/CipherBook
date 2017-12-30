package service.base;

import enums.ServiceStatus;
import enums.ServiceType;
import enums.Topid;
import actions.RequestAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settings.ServiceSetting;

import java.util.concurrent.LinkedBlockingQueue;

public abstract class BaseService implements Runnable, Serviceable {
    protected Logger logger = LogManager.getLogger(BaseService.class);

    private LinkedBlockingQueue<RequestAction> requestQueue = new LinkedBlockingQueue<RequestAction>();

    private Thread thread;
    private ServiceStatus status;
    private ServiceType type;
    private String name;
    private boolean running = true;

    public BaseService(ServiceType type, String name) {
        this.name = name;
        this.type = type;
        this.status = ServiceStatus.stop;
    }

    public abstract Topid getTopid();

    public void addRequestAction(RequestAction requestAction) {
        try {
            this.requestQueue.put(requestAction);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
     * Runnable
     */
    public void run() {
        this.status = ServiceStatus.ready;
        ready();
        logger.info(this.getName() + "运行中...");
        this.status = ServiceStatus.running;
        running();
    }

    /*
     * Serviceable
     */
    public void ready() {

    }

    public void running() {
        while (running) {
            try {
                RequestAction requestAction = requestQueue.take();
                processing(requestAction);
                processed(requestAction);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void processed(RequestAction requestAction) {
        ServiceSetting.getInstance().getServerBuffer(requestAction.getBufferId()).addSendAction(requestAction);
    }

    public void start() {
        Thread thread = new Thread(this, this.getClass().getSimpleName() + ".thread");
        thread.start();
        this.thread = thread;
        logger.info("[" + this.getClass().getName() + ":" + this.getName() + "]启动...");
    }

    public void stop() {
        this.status = ServiceStatus.stop;
        this.running = false;
        this.thread.interrupt();
        this.requestQueue.clear();
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
