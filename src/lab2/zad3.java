//package lab2;
//
//class Incrementator{
//    public int increment(int n){
//        return n++;
//    }
//}
//
//class Counter3 extends Thread{
//
//    public void run(){
//        Incrementator incrementator = new Incrementator();
//        for (int i = 0; i < 100; i++){
//            int n = incrementator.increment(n);
//        }
//    }
//}
//
//public class zad3 {
//    public static void main(String[] args) {
//        Thread thread1 = new Counter3();
//        Thread thread2 = new Counter3();
//        thread1.start();
//        thread2.start();
//
//    }
//}
