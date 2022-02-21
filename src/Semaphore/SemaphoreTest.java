package Semaphore;

import java.util.Scanner;

public class SemaphoreTest {

    public static void main(String[] args) {
        int numResources;
        int produceRate;
        int consumeRate;
        Scanner in = new Scanner(System.in);
        System.out.println("Number of resources: ");
        numResources = in.nextInt();
        System.out.println("Produce rate (ms): ");
        produceRate = in.nextInt();
        System.out.println("Consume rate (ms): ");
        consumeRate = in.nextInt();

        Semaphore sem = new Semaphore(numResources);
        Consumer consumer = new Consumer(sem, consumeRate);
        Producer producer = new Producer(sem, produceRate);
    }
}
