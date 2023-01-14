package Count2a;

//mechanizm monitorow - ugolonienie obiketu, z dodatkie
// ze tylko jeden proces wykonuje w danym czasie operacje na obiekcie
//kady obiket ze synchronoziwoymi metodami jest monitorem
//dzieki synchronized klasa jest przystosowana to wielowatkowsci,
// wymusza WZAJEMNE WYKLUCZNIAE - insturkcje z sekcji krytrycznych procesow nie przeplataja sie

class IntCell {
//    private int n = 0;
//    private boolean isFree = true;
//
//    public int getN(){
//        while(!isFree){
//            waitForRelease();
//        }
//        isFree = false;
//        notifyAll();
//        return n;
//    }
//
//    public void setN(int n){
//        while (!isFree){
//            waitForRelease(); //waitForRelease zamiast tez git
//        }
//        isFree = true;
//        this.n = n;
//        notifyAll();
//    }
//
//    private void waitForRelease(){
//        try{
//            wait();
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
//    }
    private int n = 0;
    public int getN() {return n;}
    public void setN(int n) {this.n = n;}
}

public class Count2a extends Thread {
    private static IntCell n = new IntCell();

    //    @Override     ORIGINAL FOR WORSE SOLUTION
//    public void run() {
//        int temp;
//        for (int i = 0; i < 200000; i++) {
//            temp = n.getN();
//            n.setN(temp + 1);
//        }
//    }

    @Override
    public void run() {
        int temp;
        for (int i = 0; i < 200000; i++) {
            synchronized (n){
                temp = n.getN();
                n.setN(temp + 1);
            }
        }
    }

    public static void main(String[] args) {
        Count2a p = new Count2a();
        Count2a q = new Count2a();
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
