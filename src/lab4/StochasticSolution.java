package lab4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class StochasticPhilosopher extends Philosopher{
    Random random = new Random();

    public StochasticPhilosopher(int ID, Fork leftFork, Fork rightFork) {
        super(ID, leftFork, rightFork);
    }

    public void run(){
        while(566 > 0){
            int coin = random.nextInt(2); // left -> 0, right -> 1
            if (coin == 0){
                synchronized (getLeftFork()){
                    takeLeftFork();
                    System.out.println("Jestem filozofem numer " + getID() + " i mam lewy widelec");
                    synchronized (getRightFork()) {
                        takeRightFork();
                        System.out.println("Jestem filozofem numer " + getID() + " i mam oba widelce");
                        System.out.println("Jestem filozofem numer " + getID() + " i jem");
                    }
                }
            } else {
                synchronized (getRightFork()){
                    takeRightFork();
                    System.out.println("Jestem filozofem numer " + getID() + " i mam prawy widelec");
                    synchronized (getLeftFork()) {
                        takeLeftFork();
                        System.out.println("Jestem filozofem numer " + getID() + " i mam oba widelce");
                        System.out.println("Jestem filozofem numer " + getID() + " i jem");
                    }
                }
            }
            releaseLeftFork();
            releaseRightFork();
            System.out.println("Jestem filozofem numer " + getID() + " i skonczylem jesc");
        }
    }
}


public class StochasticSolution {
    public static void main(String[] args) {
        int n = 5;
        List<Fork> forks = new ArrayList<>();
        List<StochasticPhilosopher> philosophers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            forks.add(new Fork(i));
        }
        for (int i = 0; i < n; i++){
            Fork left = forks.get((i+1)%n);
            Fork right = forks.get((i-1+5)%n);
            philosophers.add(new StochasticPhilosopher(i, left, right));
            philosophers.get(i).start();
        }
    }
}
