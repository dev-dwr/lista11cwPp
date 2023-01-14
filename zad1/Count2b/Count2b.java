package Count2b;

import java.util.concurrent.Semaphore;

class IntCell {
    private int n = 0;

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}

public class Count2b extends Thread {
    private static Semaphore semaphore = new Semaphore(1);
    private static IntCell n = new IntCell();

    @Override
    public void run() {
        int temp;
        for (int i = 0; i < 200000; i++) {
            semathoreAcquire();
            temp = n.getN();
            n.setN(temp + 1);
            semaphore.release();
        }
    }

    private static void semathoreAcquire() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
        }
    }

    public static void main(String[] args) {
        Count2b p = new Count2b();
        Count2b q = new Count2b();
        p.start();
        q.start();
        try {
            p.join();
            q.join();
        } catch (InterruptedException e) {
        }
        System.out.println("The value of n is " + n.getN());
    }
}
