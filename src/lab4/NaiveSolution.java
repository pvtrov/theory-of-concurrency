package lab4;


import java.util.ArrayList;
import java.util.List;

class Fork{
    private boolean isTaken;
    private int ID;

    public Fork(int ID) {
        this.ID = ID;
        isTaken = false;
    }

    public int getID() {
        return ID;
    }
    public Fork() {
        isTaken = false;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void take() {
        isTaken = true;
    }

    public void release() {
        isTaken = false;
    }
}

class Philosopher extends Thread {
    private int ID;
    private Fork leftFork;
    private Fork rightFork;

    public Philosopher(int ID, Fork leftFork, Fork rightFork) {
        this.ID = ID;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    public int getID() {
        return ID;
    }

    public void takeLeftFork() {
         leftFork.take();
    }

    public void takeRightFork() {
        rightFork.take();
    }

    public void releaseLeftFork() {
        leftFork.release();
    }

    public void releaseRightFork() {
        rightFork.release();
    }

    public void run(){
        while(true){
            synchronized (leftFork){
                leftFork.take();
                System.out.println("Jestem filozofem numer " + this.ID + " i mam lewy widelec");
                synchronized (rightFork){
                    rightFork.take();
                    System.out.println("Jestem filozofem numer " + this.ID + " i mam oba widelce");
                    System.out.println("Jestem filozofem numer " + this.ID + " i jem");
                    leftFork.release();
                    rightFork.release();
                    System.out.println("Jestem filozofem numer " + this.ID + " i skonczylem jesc");
                }
            }
        }
    }
}


public class NaiveSolution {
    public static void main(String[] args) {
        int n = 5;
        List<Fork> forks = new ArrayList<>();
        List<Philosopher> philosophers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            forks.add(new Fork(i));
        }
        for (int i = 0; i < n; i++){
            Fork left = forks.get((i+1)%n);
            Fork right = forks.get((i-1+5)%n);
            philosophers.add(new Philosopher(i, left, right));
            philosophers.get(i).start();
        }
    }
}


