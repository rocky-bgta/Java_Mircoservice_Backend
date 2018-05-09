package nybsys.tillboxweb.broker.client;

public class WorkersStatus extends Thread {

    private Object lock;

    public WorkersStatus(Object lock) {
        this.lock = lock;
    }

    public void run() {
        synchronized (this.lock) {
            this.lock.notifyAll();
            System.out.println("All Worker complete there work ...");
        }
    }
}
