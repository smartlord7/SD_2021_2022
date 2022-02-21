package Semaphore;

public class Producer implements Runnable {
    private Semaphore semaphore;
    private int produceRate;

    public Producer(Semaphore semaphore, int produceRate) {
        this.semaphore = semaphore;
        this.produceRate = produceRate;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(produceRate);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true) {
            semaphore.doSignal();
            System.out.println("Producer: " + semaphore.getValue());
            try {
                Thread.sleep(produceRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
