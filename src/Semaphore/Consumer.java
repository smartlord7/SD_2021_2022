package Semaphore;

public class Consumer implements Runnable {
    private Semaphore semaphore;
    private int consumeRate;

    public Consumer(Semaphore semaphore, int consumeRate) {
        this.semaphore = semaphore;
        this.consumeRate = consumeRate;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            semaphore.doWait();
            System.out.println("Consumer: " + semaphore.getValue());
            try {
                Thread.sleep(consumeRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
