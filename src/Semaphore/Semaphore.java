package Semaphore;

public class Semaphore {
    private volatile int value;

    public Semaphore(int value) {
        this.value = value;
    }

    public Semaphore() {
        this.value = 1;
    }

    public void doWait() {
        synchronized (this) {
            if (this.value > 0) {
                this.value--;
                return;
            }

            while (this.value == 0) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            this.value--;
        }
    }

    public void doSignal() {
        synchronized (this) {
            this.value++;
            this.notifyAll();
        }
    }

    public synchronized int getValue() {
        return value;
    }
}
