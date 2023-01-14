public class BoundedBuffer implements Produce, Consume {
    final private int N;
    private int in = 0, out = 0, n = 0;
    private int[] elems;

    public BoundedBuffer(int N) {
        this.N = N;
        elems = new int[N];
    }

    public synchronized void put(int x) {
        //N to jest 5
        // n to liczba wyprodukowanych juz wiadmosci przez producera
        while (n >= N) //liczba wiadomosi do wyprodukowania przez producenta
            try {
                //jezeli liczba juz wyprodukowany wiadomosci jest wieksza niz limit N wtedy czekamy aby zotaly skonsumowane
                System.out.println(Thread.currentThread().getName() + " waiting with " + x);
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        //w przeciwnym wypadku produkujemy wiadomosci
        elems[in] = x;
        in = (in + 1) % N;
        n += 1;
        System.out.println(Thread.currentThread().getName() + " produced: " + x);
        if (n == 1) notifyAll();
    }

    public synchronized int take() {
        while (n == 0) //liczba wyprodukowanych wiadmosci przez kolejno producera
            try {
                //jezeli konsumera nie ma wiadomsci do skonsumowania to czekamy na nie
                System.out.println(Thread.currentThread().getName() + " waiting");
                wait(); //czekamy na zajesci wydarznia, zmienia stan watku ktory wywal to metode na oczekaujaca dla nagego obiektu
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        // w przeciwnym wypadku konsumujemy wiadmosci i zwracy to co zostalo skonsumowane
        int x = elems[out];
        out = (out + 1) % N;
        n -= 1;
        System.out.println(Thread.currentThread().getName() + " consuming: " + x);
        //kiedy liczba wiadmosci jest rowna 4 wtedy powiadmoamiamy wszysktm oczekujacy watka ze wystapilo wydarzenie
        if (n == N - 1) notifyAll();
        //uzycie metody notify zamiast notifyAll nie byloby bezpieczne program zostanie zawieszony wystapi deadLock
        //notify zasygnalizuje tylko jednemu watkowi ze nastpilo wydarzenie podczas gdy operujemy na tablicy Productetow i Consumerow ktorych jest 3
        // tylko jeden konsumer/producer zostanie powiadominych a 2 i 3 beda czekac dlaej na dalsze wydarzenia ktore nie otrzymaja program sie zawiesi
        return x;
    }
}

