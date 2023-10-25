//package lab3;
//
//class MyObject2{
//    public Integer n = null;
//}
//
//class Producent2 extends Thread{
//    private MyObject2 _buf;
//
//    Producent2(MyObject2 _buf){
//        this._buf = _buf;
//    }
//
//    public void run(){
//        for (int i = 0; i < 100; ++i ) {
//            synchronized (_buf) {
//                while (_buf.n != null) {
//                    try {
//                        _buf.wait();
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    } }
//                _buf.n = i;
//                System.out.println("Ustawiłem inta " + _buf.n);
//
//                _buf.notify();
//
//            }
//        }
//    }
//}
//
//class Consument2 extends Thread{
//    private MyObject2 _buf;
//
//    Consument2(MyObject2 _buf){
//        this._buf = _buf;
//    }
//
//    public void run(){
//        for (int i = 0; i < 100; ++ i ) {
//            synchronized (_buf) {
//                while (_buf.n == null) {
//                    try {
//                        _buf.wait();
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    } }
//                System.out.println ("Otrzymałem inta " + _buf.n);
//                _buf.n = null;
//                _buf.notify();
//
//            }
//        }
//    }
//}
//
//public class zad2 {
//    public static void main(String[] args) {
//        MyObject2 buff = new MyObject2();
//        Thread producer = new Producent2(buff);
//        Thread conyment = new Consument2(buff);
//
//        producer.start();
//        conyment.start();
//    }
//}
