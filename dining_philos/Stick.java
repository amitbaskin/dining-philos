package dining_philos;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A stick
 */
public class Stick {
    private final int index;
    private boolean isGrabbed;
    private final Lock grabLock = new ReentrantLock();
    private final Condition canGrab = grabLock.newCondition();

    /**
     * Create a new stick
     * @param index The index of this stick
     */
    public Stick(int index){
        this.index = index;
    }

    /**
     * Get the index of this stick
     * @return The index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Grab this stick
     * @throws InterruptedException In case the thread grabbing this stick is being interrupted while waiting
     */
    public void grab() throws InterruptedException {
        try{
            grabLock.lock();
            while (isGrabbed) {
                canGrab.await();
            }
            isGrabbed = true;
        }
        finally {
            grabLock.unlock();
        }
    }

    /**
     * Put this stick down
     */
    public void putDown(){
        try{
            grabLock.lock();
            isGrabbed = false;
            canGrab.signalAll();
        }
        finally {
            grabLock.unlock();
        }

    }
}