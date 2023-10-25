package lab3;
import javax.lang.model.type.NullType;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;





class Producent2 extends Thread{
    private LinkedBlockingQueue buf;

    Producent2(LinkedBlockingQueue buf){
        this.buf = buf;
    }

    public void run(){
        for (int i = 0; i < 100; ++i ) {
            synchronized (buf) {
                while (buf.size() >= 100) {      // nie ma miejsca
                    try {
                        buf.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } }
                try {
                    buf.put(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                buf.notifyAll();
                System.out.println(Thread.currentThread().getName() + " ustawiłem inta " + i);

            }
            try {
                Thread.currentThread().sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}

class Consument2 extends Thread{
    private LinkedBlockingQueue buf;

    Consument2(LinkedBlockingQueue buf){
        this.buf = buf;
    }

    public void run(){
        for (int i = 0; i < 100; ++ i ) {
            synchronized (buf) {
                while (buf.size() <= 0) {  // nic tam nie ma
                     try {
                        buf.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    System.out.println ("Otrzymałem inta " + buf.take());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                buf.notifyAll();
            }
        }
    }
}


public class zad1 {
    public static void main(String[] args) {
        LinkedBlockingQueue <Integer> buff = new LinkedBlockingQueue <Integer>() ;

        Thread producer = new Producent2(buff);
        Thread producer1 = new Producent2(buff);
        Thread producer2 = new Producent2(buff);

        Thread consument1 = new Consument2(buff);
//        Thread consument2 = new Consument(buff);
//        Thread consument3 = new Consument(buff);

        producer.start();
        producer1.start();
        producer2.start();
        consument1.start();
//        consument2.start();
//        consument3.start();
    }
}



