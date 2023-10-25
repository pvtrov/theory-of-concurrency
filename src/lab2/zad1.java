package lab2;

class Counter extends Thread{
    public void run(){
        System.out.println("5");
        System.out.println("4");
        System.out.println("3");
        System.out.println("2");
        System.out.println("1");
        System.out.println("0");
        System.out.println("Akcja!");
    }
};
class Zad1{
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++){
            Thread thread = new Counter();
            thread.start();
        }
    }
};