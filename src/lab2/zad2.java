package lab2;

class Counter2 extends Thread{
    int n;
    Counter2(int n){
        this.n = n;
    }
    public void run(){
        int start_n = 0;
            for (int i = start_n; i < n; i++){
                System.out.println(i + " " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
    }
};
class Zad2{
    public static void main(String[] args) {
        Thread thread0 = new Counter2(20);
        Thread thread1 = new Counter2(20);
        Thread thread2 = new Counter2(20);
        thread2.setPriority(3);
        thread0.setPriority(2);
        thread1.setPriority(1);
        thread0.start();
        thread2.start();
        thread1.start();
    }
};


