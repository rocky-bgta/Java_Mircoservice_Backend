package nybsys.tillboxweb.broker.client;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class InterModuleWorkerCommunicationThread extends Thread {
    CyclicBarrier waitPoint;
    Object lock;

    public InterModuleWorkerCommunicationThread(CyclicBarrier barrier, Object lock) {
        waitPoint = barrier;
        this.lock = lock;
        this.start();
    }

    public void run() {
        try {
            synchronized (this.lock) {
                this.lock.wait();
            }
            waitPoint.await(); // await until all worker done
        } catch (BrokenBarrierException | InterruptedException exception) {
            System.out.println("An exception occurred while waiting... " + exception);
        }
    }
}
